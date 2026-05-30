// Hourly Blood Oxygen (SpO2) Line/Scatter Chart - Longevity Platform

/**
 * Configure and render the Hourly SpO2 Line Chart
 * @param {HTMLCanvasElement} canvas 
 * @param {object} spo2Data 
 */
export function renderSpo2Chart(canvas, spo2Data) {
    if (!canvas || !spo2Data || !spo2Data.hourly) return null;

    const ctx = canvas.getContext('2d');
    const hourly = spo2Data.hourly;

    // Prepare 24-hour datasets (0 to 23)
    const labels = Array.from({ length: 24 }, (_, i) => `${i.toString().padStart(2, '0')}:00`);
    const lineData = Array.from({ length: 24 }, () => null);
    const minData = Array.from({ length: 24 }, () => null);
    const rawHourly = Array.from({ length: 24 }, () => null);

    hourly.forEach(item => {
        const h = item.hour;
        if (h >= 0 && h < 24 && item.count > 0) {
            lineData[h] = item.avg;
            minData[h] = item.min;
            rawHourly[h] = item;
        }
    });

    const config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Ortalama SpO2',
                    data: lineData,
                    borderColor: 'var(--color-primary)',
                    backgroundColor: 'rgba(59, 130, 246, 0.2)',
                    borderWidth: 2,
                    pointBackgroundColor: 'var(--color-primary)',
                    pointBorderColor: 'rgba(255,255,255,0.7)',
                    pointRadius: 4,
                    pointHoverRadius: 6,
                    fill: false,
                    spanGaps: true,
                    order: 1
                },
                {
                    label: 'En Düşük SpO2',
                    data: minData,
                    borderColor: 'rgba(236, 72, 153, 0.5)', // pinkish color
                    borderWidth: 1,
                    pointBackgroundColor: 'var(--color-pink)',
                    pointRadius: 3,
                    borderDash: [3, 3],
                    fill: false,
                    spanGaps: true,
                    order: 2
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        color: '#94a3b8',
                        font: { family: 'Inter', size: 11, weight: '500' }
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
                        title: (tooltipItems) => {
                            const hour = tooltipItems[0].dataIndex;
                            return `${hour.toString().padStart(2, '0')}:00 - ${(hour + 1).toString().padStart(2, '0')}:00`;
                        },
                        label: (context) => {
                            const index = context.dataIndex;
                            const hData = rawHourly[index];
                            if (!hData) return 'Ölçüm yok';

                            if (context.datasetIndex === 0) {
                                return `Ortalama: ${hData.avg.toFixed(1)}%`;
                            } else {
                                return `En Düşük: ${hData.min}% (${hData.count} ölçüm)`;
                            }
                        }
                    }
                },
                // Annotation for normal oxygen range (95% - 100%)
                annotation: {
                    annotations: {
                        normalRange: {
                            type: 'box',
                            yMin: 95,
                            yMax: 100,
                            backgroundColor: 'rgba(16, 185, 129, 0.04)', // extremely faint green
                            borderColor: 'rgba(16, 185, 129, 0.1)',
                            borderWidth: 1,
                            label: {
                                display: true,
                                content: 'Normal Seviye (95-100%)',
                                position: 'start',
                                color: 'rgba(16, 185, 129, 0.4)',
                                font: { size: 9, family: 'Inter', weight: 'bold' }
                            }
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: { color: 'rgba(255, 255, 255, 0.03)' },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 },
                        maxRotation: 0,
                        autoSkip: true,
                        maxTicksLimit: 12
                    }
                },
                y: {
                    min: 90, // Bound tightly
                    max: 100,
                    grid: { color: 'rgba(255, 255, 255, 0.03)' },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 },
                        stepSize: 2
                    }
                }
            }
        }
    };

    return new Chart(ctx, config);
}
