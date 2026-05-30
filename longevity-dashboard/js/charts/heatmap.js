export function renderHeatmap(canvasId, healthData) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    if (window.heatmapChartInstance) {
        window.heatmapChartInstance.destroy();
    }

    // Prepare data for heatmap
    // X axis: Hours 0-23
    // Y axis: Days (last 30 days)
    
    // We will use a bubble chart or a matrix plugin. Since we don't have matrix plugin included,
    // we can simulate a heatmap using a scatter/bubble chart with square points, or just use 
    // a basic bar chart if we really can't. Let's do a bubble chart where bubble size and color
    // depends on activity level (e.g. step count or energy).

    const dataPoints = [];
    const days = [];

    // Filter to last 30 entries max
    const recentData = healthData.slice(-30);

    recentData.forEach((dayData, dayIndex) => {
        const dateStr = new Date(dayData.date).toLocaleDateString('tr-TR', { day: 'numeric', month: 'short' });
        days.push(dateStr);
        
        // If we have hourly steps, plot them
        if (dayData.hourlySteps && dayData.hourlySteps.length > 0) {
            dayData.hourlySteps.forEach(h => {
                dataPoints.push({
                    x: h.hour,
                    y: dayIndex,
                    r: Math.min(10, Math.max(3, h.count / 100)), // Scale radius based on steps
                    steps: h.count
                });
            });
        }
    });

    window.heatmapChartInstance = new Chart(ctx, {
        type: 'bubble',
        data: {
            datasets: [{
                label: 'Aktivite (Adım)',
                data: dataPoints,
                backgroundColor: (context) => {
                    const value = context.raw?.steps || 0;
                    // Color gradient based on steps
                    if (value > 1000) return 'rgba(239, 68, 68, 0.8)'; // Red
                    if (value > 500) return 'rgba(249, 115, 22, 0.8)'; // Orange
                    if (value > 200) return 'rgba(234, 179, 8, 0.8)'; // Yellow
                    return 'rgba(74, 222, 128, 0.8)'; // Green
                },
                borderColor: 'transparent'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `${context.raw.steps} adım (Saat: ${context.raw.x})`;
                        }
                    }
                }
            },
            scales: {
                x: {
                    min: -1,
                    max: 24,
                    ticks: {
                        stepSize: 1,
                        color: 'rgba(255,255,255,0.6)'
                    },
                    grid: { color: 'rgba(255,255,255,0.05)' },
                    title: { display: true, text: 'Saat', color: 'rgba(255,255,255,0.8)' }
                },
                y: {
                    min: -1,
                    max: days.length,
                    ticks: {
                        stepSize: 1,
                        callback: function(value) {
                            return days[value] || '';
                        },
                        color: 'rgba(255,255,255,0.6)'
                    },
                    grid: { color: 'rgba(255,255,255,0.05)' }
                }
            }
        }
    });
}
