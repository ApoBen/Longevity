export function renderVolumeChart(canvasId, workouts) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    if (window.volumeChartInstance) {
        window.volumeChartInstance.destroy();
    }

    // Sort by date ascending for the chart
    const sortedWorkouts = [...workouts].sort((a, b) => new Date(a.date) - new Date(b.date));
    
    // Group by date (or just map if we want each workout)
    // To keep it simple, we'll plot each workout's total volume by date.
    
    const labels = [];
    const volumes = [];

    sortedWorkouts.forEach(w => {
        let totalVol = 0;
        w.exercises.forEach(ex => {
            ex.sets.forEach(set => {
                totalVol += (set.kg * set.reps);
            });
        });
        labels.push(new Date(w.date).toLocaleDateString('tr-TR'));
        volumes.push(totalVol);
    });

    window.volumeChartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Tonaj (kg)',
                data: volumes,
                backgroundColor: 'rgba(59, 130, 246, 0.6)',
                borderColor: 'rgba(59, 130, 246, 1)',
                borderWidth: 1,
                borderRadius: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: { color: 'rgba(255,255,255,0.1)' },
                    ticks: { color: 'rgba(255,255,255,0.6)' }
                },
                x: {
                    grid: { display: false },
                    ticks: { color: 'rgba(255,255,255,0.6)' }
                }
            }
        }
    });
}
