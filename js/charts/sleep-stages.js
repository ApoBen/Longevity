// Sleep Stages Horizontal Stacked Bar Chart - Longevity Platform

/**
 * Configure and render the Sleep Stages Stacked Bar Chart
 * @param {HTMLCanvasElement} canvas 
 * @param {object} sleepData 
 */
export function renderSleepStagesChart(canvas, sleepData) {
    if (!canvas || !sleepData || !sleepData.stages) return null;

    const ctx = canvas.getContext('2d');
    const stages = sleepData.stages;

    // Convert values
    const deepMin = stages.deep || 0;
    const lightMin = stages.light || 0;
    const remMin = stages.rem || 0;
    const awakeMin = stages.awake || 0;

    const config = {
        type: 'bar',
        data: {
            labels: ['Uyku Evreleri (Dk)'],
            datasets: [
                {
                    label: 'Derin Uyku',
                    data: [deepMin],
                    backgroundColor: '#1e3a8a', // deep blue
                    borderColor: 'rgba(255, 255, 255, 0.05)',
                    borderWidth: 1,
                    borderRadius: { topLeft: 4, bottomLeft: 4 }
                },
                {
                    label: 'Hafif Uyku',
                    data: [lightMin],
                    backgroundColor: '#3b82f6', // light blue
                    borderColor: 'rgba(255, 255, 255, 0.05)',
                    borderWidth: 1
                },
                {
                    label: 'REM',
                    data: [remMin],
                    backgroundColor: '#a855f7', // purple
                    borderColor: 'rgba(255, 255, 255, 0.05)',
                    borderWidth: 1
                },
                {
                    label: 'Uyanık',
                    data: [awakeMin],
                    backgroundColor: '#ef4444', // red
                    borderColor: 'rgba(255, 255, 255, 0.05)',
                    borderWidth: 1,
                    borderRadius: { topRight: 4, bottomRight: 4 }
                }
            ]
        },
        options: {
            indexAxis: 'y', // Makes it horizontal
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        color: '#94a3b8',
                        font: { family: 'Inter', size: 10, weight: '500' }
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(13, 17, 39, 0.95)',
                    titleColor: '#f8fafc',
                    bodyColor: '#94a3b8',
                    borderColor: 'rgba(255, 255, 255, 0.08)',
                    borderWidth: 1,
                    padding: 12,
                    cornerRadius: 8,
                    callbacks: {
                        label: (context) => {
                            const val = context.raw;
                            const hours = Math.floor(val / 60);
                            const mins = val % 60;
                            const timeStr = hours > 0 ? `${hours}s ${mins}dk` : `${mins}dk`;
                            return `${context.dataset.label}: ${timeStr} (${val} dk)`;
                        }
                    }
                }
            },
            scales: {
                x: {
                    stacked: true,
                    grid: { color: 'rgba(255, 255, 255, 0.03)' },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 }
                    }
                },
                y: {
                    stacked: true,
                    grid: { display: false },
                    ticks: {
                        display: false // Hide label as we have horizontal stacked legend
                    }
                }
            }
        }
    };

    return new Chart(ctx, config);
}
