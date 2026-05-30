import { DB } from '../db.js';

let pharmaChartInstance = null;

export async function renderPharmaChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    pharmaChartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: []
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            plugins: {
                legend: {
                    position: 'top',
                    labels: { color: 'rgba(255, 255, 255, 0.7)' }
                },
                tooltip: {
                    mode: 'index',
                    intersect: false
                }
            },
            scales: {
                x: {
                    grid: { color: 'rgba(255, 255, 255, 0.1)' },
                    ticks: { color: 'rgba(255, 255, 255, 0.5)' }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Plazma Konsantrasyonu (mg)',
                        color: 'rgba(255, 255, 255, 0.5)'
                    },
                    grid: { color: 'rgba(255, 255, 255, 0.1)' },
                    ticks: { color: 'rgba(255, 255, 255, 0.5)' }
                }
            }
        }
    });

    await updatePharmaChart();
}

export async function updatePharmaChart() {
    if (!pharmaChartInstance) return;

    try {
        const meds = await DB.getByIndex('medications', 'active', 1);
        const logs = await DB.getAll('medication_logs');

        // Setup time range: 24h ago to +24h future
        const now = new Date();
        const startTime = new Date(now.getTime() - 24 * 60 * 60 * 1000);
        const endTime = new Date(now.getTime() + 24 * 60 * 60 * 1000);
        
        // Generate hourly labels
        const labels = [];
        const timePoints = [];
        for (let d = new Date(startTime); d <= endTime; d.setMinutes(d.getMinutes() + 30)) {
            timePoints.push(d.getTime());
            labels.push(d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }));
        }

        const datasets = meds.map(med => {
            const medLogs = logs.filter(l => l.medicationId === med.id);
            const data = timePoints.map(t => {
                let concentration = 0;
                medLogs.forEach(log => {
                    const logTime = new Date(log.timestamp).getTime();
                    if (logTime <= t) {
                        const hoursPassed = (t - logTime) / (1000 * 60 * 60);
                        concentration += Number(med.dose) * Math.pow(0.5, hoursPassed / med.halfLife);
                    }
                });
                return concentration;
            });

            return {
                label: med.name,
                data: data,
                borderColor: med.color,
                backgroundColor: med.color + '33', // 20% opacity
                borderWidth: 2,
                fill: true,
                pointRadius: 0, // hide points for smooth curve
                pointHitRadius: 10,
                tension: 0.4
            };
        });

        // Add a vertical line annotation for "NOW" using Chart.js annotation plugin if available,
        // or just by finding the index
        const nowTime = now.getTime();
        let nowIndex = timePoints.findIndex(t => t >= nowTime);
        if (nowIndex === -1) nowIndex = timePoints.length / 2;

        pharmaChartInstance.data.labels = labels;
        pharmaChartInstance.data.datasets = datasets;

        // Custom plugin to draw vertical line at "now"
        pharmaChartInstance.options.plugins.annotation = {
            annotations: {
                line1: {
                    type: 'line',
                    xMin: nowIndex,
                    xMax: nowIndex,
                    borderColor: 'rgba(239, 68, 68, 0.8)',
                    borderWidth: 2,
                    borderDash: [5, 5],
                    label: {
                        display: true,
                        content: 'Şu an',
                        position: 'start'
                    }
                }
            }
        };

        pharmaChartInstance.update();

    } catch (error) {
        console.error('Error updating pharma chart:', error);
    }
}
