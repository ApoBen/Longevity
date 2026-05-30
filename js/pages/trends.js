import { DB } from '../db.js';
import { renderHeatmap } from '../charts/heatmap.js';

export async function render(container) {
    container.innerHTML = `
        <div class="trends-page-container">
            <div class="welcome-header glass-card" style="margin-bottom: 24px;">
                <h2>Trendler & Uzun Vadeli Analiz</h2>
                <p style="opacity: 0.8; font-size: 0.9rem; margin-top: 4px;">Zaman içindeki gelişiminizi ve genel sağlık yöneliminizi inceleyin.</p>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px; margin-bottom: 24px;">
                <div class="glass-card">
                    <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Uyku Kalitesi Trendi</h3>
                    <div class="chart-container" style="height: 250px; position: relative;">
                        <canvas id="sleep-trend-chart"></canvas>
                    </div>
                </div>

                <div class="glass-card">
                    <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Dinlenme Nabzı Trendi</h3>
                    <div class="chart-container" style="height: 250px; position: relative;">
                        <canvas id="rhr-trend-chart"></canvas>
                    </div>
                </div>
            </div>

            <div class="glass-card">
                <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Aktivite Isı Haritası (Son 30 Gün)</h3>
                <div style="overflow-x: auto;">
                    <canvas id="activity-heatmap-chart" style="min-width: 800px; height: 300px;"></canvas>
                </div>
            </div>
        </div>
    `;

    lucide.createIcons();

    const healthData = await DB.getAll('health_data');
    healthData.sort((a,b) => new Date(a.date) - new Date(b.date));

    renderSleepTrend(healthData);
    renderRHRTrend(healthData);
    renderHeatmap('activity-heatmap-chart', healthData);
}

function renderSleepTrend(data) {
    const ctx = document.getElementById('sleep-trend-chart');
    if(!ctx || data.length === 0) return;

    const labels = data.map(d => new Date(d.date).toLocaleDateString('tr-TR', {day: 'numeric', month: 'short'}));
    const scores = data.map(d => d.sleepScore || 0);

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Uyku Skoru',
                data: scores,
                borderColor: '#8b5cf6',
                backgroundColor: 'rgba(139, 92, 246, 0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { min: 0, max: 100, grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: 'rgba(255,255,255,0.6)' } },
                x: { grid: { display: false }, ticks: { color: 'rgba(255,255,255,0.6)', maxTicksLimit: 10 } }
            }
        }
    });
}

function renderRHRTrend(data) {
    const ctx = document.getElementById('rhr-trend-chart');
    if(!ctx || data.length === 0) return;

    const labels = data.map(d => new Date(d.date).toLocaleDateString('tr-TR', {day: 'numeric', month: 'short'}));
    const rhr = data.map(d => d.restingHeartRate || 0);

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Dinlenme Nabzı (BPM)',
                data: rhr,
                borderColor: '#ef4444',
                backgroundColor: 'rgba(239, 68, 68, 0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { min: 40, max: 100, grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: 'rgba(255,255,255,0.6)' } },
                x: { grid: { display: false }, ticks: { color: 'rgba(255,255,255,0.6)', maxTicksLimit: 10 } }
            }
        }
    });
}
