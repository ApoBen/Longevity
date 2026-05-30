// Heart Rate Range (Floating Bar) Chart - Longevity Platform

/**
 * Configure and render the Heart Rate Floating Bar Chart
 * @param {HTMLCanvasElement} canvas 
 * @param {object} heartRateData 
 */
export function renderHeartRateChart(canvas, heartRateData) {
    if (!canvas || !heartRateData || !heartRateData.hourly) return null;

    const ctx = canvas.getContext('2d');
    const hourly = heartRateData.hourly;

    // 1. Prepare 24-hour datasets (0 to 23)
    const labels = Array.from({ length: 24 }, (_, i) => `${i.toString().padStart(2, '0')}:00`);
    
    // Floating bars: [min, max]
    const barData = Array.from({ length: 24 }, () => null);
    // Line dataset: average
    const lineData = Array.from({ length: 24 }, () => null);
    // Reference list of hourly records for tooltips
    const rawHourly = Array.from({ length: 24 }, () => null);

    hourly.forEach(item => {
        const h = item.hour;
        if (h >= 0 && h < 24 && item.count > 0) {
            barData[h] = [item.min, item.max];
            lineData[h] = item.avg;
            rawHourly[h] = item;
        }
    });

    // 2. Setup Chart.js configuration
    const config = {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Ortalama Nabız',
                    data: lineData,
                    type: 'line',
                    borderColor: '#ffffff',
                    borderWidth: 2,
                    pointBackgroundColor: '#ffffff',
                    pointBorderColor: 'rgba(255,255,255,0.7)',
                    pointRadius: 4,
                    pointHoverRadius: 6,
                    fill: false,
                    order: 1,
                    spanGaps: true
                },
                {
                    label: 'Nabız Aralığı',
                    data: barData,
                    backgroundColor: (context) => {
                        const index = context.dataIndex;
                        const avg = lineData[index];
                        if (!avg) return 'transparent';
                        if (avg > 120) return 'rgba(239, 68, 68, 0.7)';  // Danger Red
                        if (avg > 90) return 'rgba(245, 158, 11, 0.7)';  // Warning Orange
                        return 'rgba(16, 185, 129, 0.7)';  // Success Green
                    },
                    borderColor: (context) => {
                        const index = context.dataIndex;
                        const avg = lineData[index];
                        if (!avg) return 'transparent';
                        if (avg > 120) return 'var(--color-danger)';
                        if (avg > 90) return 'var(--color-warning)';
                        return 'var(--color-success)';
                    },
                    borderWidth: 1,
                    borderRadius: 4,
                    borderSkipped: false,
                    barPercentage: 0.7,
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
                                return `Ortalama: ${hData.avg} BPM`;
                            } else {
                                return `Aralık: ${hData.min} - ${hData.max} BPM (${hData.count} ölçüm)`;
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
                    min: 40,
                    max: 180,
                    grid: { color: 'rgba(255, 255, 255, 0.03)' },
                    ticks: {
                        color: '#627288',
                        font: { family: 'Inter', size: 10 },
                        stepSize: 20
                    }
                }
            }
        }
    };

    return new Chart(ctx, config);
}
