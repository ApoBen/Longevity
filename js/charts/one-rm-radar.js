export function render1RmRadarChart(canvasId, workouts) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    if (window.rmRadarChartInstance) {
        window.rmRadarChartInstance.destroy();
    }

    // A simple radar chart showing relative strength across basic movements
    // e.g. Bench Press, Squat, Deadlift, Overhead Press
    // For simplicity, we just look up the max weight lifted for these.

    const maxes = {
        'Bench Press': 0,
        'Squat': 0,
        'Deadlift': 0,
        'Overhead Press': 0
    };

    workouts.forEach(w => {
        w.exercises.forEach(ex => {
            if (maxes[ex.name] !== undefined) {
                let maxKg = 0;
                ex.sets.forEach(set => {
                    if (set.kg > maxKg) maxKg = set.kg;
                });
                if (maxKg > maxes[ex.name]) {
                    maxes[ex.name] = maxKg;
                }
            }
        });
    });

    const labels = Object.keys(maxes);
    const data = Object.values(maxes);

    window.rmRadarChartInstance = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Maksimum Ağırlık (kg)',
                data: data,
                backgroundColor: 'rgba(239, 68, 68, 0.2)',
                borderColor: 'rgba(239, 68, 68, 1)',
                pointBackgroundColor: 'rgba(239, 68, 68, 1)',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                r: {
                    angleLines: { color: 'rgba(255,255,255,0.1)' },
                    grid: { color: 'rgba(255,255,255,0.1)' },
                    pointLabels: { color: 'rgba(255,255,255,0.8)' },
                    ticks: { display: false }
                }
            },
            plugins: {
                legend: { display: false }
            }
        }
    });
}
