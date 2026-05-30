// Hourly Steps Bar Chart - Longevity Platform

/**
 * Configure and render the Hourly Steps Bar Chart
 * @param {HTMLCanvasElement} canvas 
 * @param {object} stepsData 
 */
export function renderStepsChart(canvas, stepsData) {
    if (!canvas || !stepsData) return null;

    const ctx = canvas.getContext('2d');
    const hourly = stepsData.hourly || [];
    
    // Prepare 24-hour datasets (0 to 23)
    const labels = Array.from({ length: 24 }, (_, i) => `${i.toString().padStart(2, '0')}:00`);
    const dataValues = Array.from({ length: 24 }, () => 0);
    const rawHourly = Array.from({ length: 24 }, () => null);

    hourly.forEach(item => {
        const h = item.hour;
        if (h >= 0 && h < 24) {
            dataValues[h] = item.steps;
            rawHourly[h] = item;
        }
    });

    // Create a gorgeous gradient for the bars
    const gradient = ctx.createLinearGradient(0, 0, 0, 300);
    gradient.addColorStop(0, 'rgba(16, 185, 129, 0.8)'); // success green
    gradient.addColorStop(1, 'rgba(16, 185, 129, 0.1)');

    const config = {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Adım Sayısı',
                    data: dataValues,
                    backgroundColor: gradient,
                    borderColor: 'var(--color-success)',
                    borderWidth: 1.5,
                    borderRadius: { topLeft: 4, topRight: 4, bottomLeft: 0, bottomRight: 0 },
                    borderSkipped: 'bottom',
                    barPercentage: 0.75
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false },
                tooltip: {
                    backgroundColor: 'rgba(13, 17, 39, 0.95)',
                    titleColor: '#f8fafc',
                    bodyColor: '#94a3b8',
                    borderColor: 'rgba(255, 255, 255, 0.08)',
                    borderWidth: 1,
                    padding: 12,
                    cornerRadius: 8,
                    callbacks: {
                        title: (tooltipItems) => {
                            const hour = tooltipItems[0].dataIndex;
                            return `${hour.toString().padStart(2, '0')}:00 - ${(hour + 1).toString().padStart(2, '0')}:00`;
                        },
                        label: (context) => {
                            const val = context.raw;
                            return `Adım: ${formatNumber(val)} adım`;
                        }
                    }
                },
                // Add annotation for target hourly steps if desired
                annotation: {
                    annotations: {
                        targetLine: {
                            type: 'line',
                            yMin: 500,
                            yMax: 500,
                            borderColor: 'rgba(255, 255, 255, 0.15)',
                            borderWidth: 1,
                            borderDash: [5, 5],
                            label: {
                                display: true,
                                content: 'Saatlik Hedef (500 Adım)',
                                position: 'end',
                                color: '#627288',
                                font: { size: 9, family: 'Inter', weight: 'normal' },
                                backgroundColor: 'rgba(0,0,0,0.4)',
                                padding: { top: 2, bottom: 2, left: 4, right: 4 }
                            }
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: { display: false },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 },
                        maxRotation: 0,
                        autoSkip: true,
                        maxTicksLimit: 12
                    }
                },
                y: {
                    beginAtZero: true,
                    grid: { color: 'rgba(255, 255, 255, 0.03)' },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 }
                    }
                }
            }
        }
    };

    return new Chart(ctx, config);
}

// Helper: thousand separator
function formatNumber(num) {
    if (!num) return '0';
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
