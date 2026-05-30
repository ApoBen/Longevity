// Hourly Timeline Page Controller - Longevity Platform
import { DB } from '../db.js';
import { renderHeartRateChart } from '../charts/heart-rate-range.js';
import { renderStepsChart } from '../charts/steps-hourly.js';
import { renderSpo2Chart } from '../charts/spo2-chart.js';
import { renderSleepStagesChart } from '../charts/sleep-stages.js';

export async function render(container) {
    const selectedDate = window.LongevityState.selectedDate;

    // 1. Fetch data for selected date
    const healthData = await DB.get('health_data', selectedDate);

    // 2. Render Page Layout
    container.innerHTML = `
        <div class="timeline-container">
            <!-- Day Navigation Header -->
            <div class="date-navigator-container glass-card">
                <button id="btn-prev-day" class="btn btn-secondary btn-icon">
                    <i data-lucide="chevron-left"></i>
                </button>
                <div class="current-date-info">
                    <h2>${formatDateReadable(selectedDate)}</h2>
                    <p class="subtitle">Saatlik Detaylı Rapor</p>
                </div>
                <button id="btn-next-day" class="btn btn-secondary btn-icon">
                    <i data-lucide="chevron-right"></i>
                </button>
            </div>

            ${healthData ? renderChartsGrid(healthData) : renderEmptyState()}
        </div>
    `;

    // 3. Initialize active charts if data exists
    if (healthData) {
        // A. Heart Rate Range Chart
        const hrCanvas = document.getElementById('heart-rate-chart');
        if (hrCanvas && healthData.heartRate) {
            renderHeartRateChart(hrCanvas, healthData.heartRate);
        } else {
            showNoChartDataPlaceholder('heart-rate-chart-container', 'Nabız Verisi Bulunmuyor');
        }

        // B. Steps Chart
        const stepsCanvas = document.getElementById('steps-chart');
        if (stepsCanvas && healthData.steps) {
            renderStepsChart(stepsCanvas, healthData.steps);
        } else {
            showNoChartDataPlaceholder('steps-chart-container', 'Adım Verisi Bulunmuyor');
        }

        // C. SpO2 Chart
        const spo2Canvas = document.getElementById('spo2-chart');
        if (spo2Canvas && healthData.bloodOxygen) {
            renderSpo2Chart(spo2Canvas, healthData.bloodOxygen);
        } else {
            showNoChartDataPlaceholder('spo2-chart-container', 'Oksijen Satürasyonu Verisi Bulunmuyor');
        }

        // D. Sleep Stages Chart
        const sleepCanvas = document.getElementById('sleep-stages-chart');
        if (sleepCanvas && healthData.sleep) {
            renderSleepStagesChart(sleepCanvas, healthData.sleep);
        } else {
            showNoChartDataPlaceholder('sleep-stages-container', 'Uyku Verisi Bulunmuyor');
        }
    }

    // 4. Setup Event Listeners
    setupEventListeners(container, selectedDate);
}

/**
 * Render the layout containing all hourly chart cards
 */
function renderChartsGrid(data) {
    return `
        <div class="timeline-charts-grid">
            <!-- 1. Heart Rate Chart Card -->
            <div class="chart-card glass-card">
                <div class="chart-card-title">
                    <i data-lucide="heart" class="icon-danger"></i>
                    <span>Gün İçi Nabız Değişimi (Saatlik Dilim)</span>
                </div>
                <div class="chart-container-large" id="heart-rate-chart-container">
                    <canvas id="heart-rate-chart"></canvas>
                </div>
            </div>

            <!-- 2. Steps Chart Card -->
            <div class="chart-card glass-card">
                <div class="chart-card-title">
                    <i data-lucide="footprints" class="icon-success"></i>
                    <span>Saatlik Adım Dağılımı</span>
                </div>
                <div class="chart-container" id="steps-chart-container">
                    <canvas id="steps-chart"></canvas>
                </div>
            </div>

            <!-- 3. SpO2 Chart Card -->
            <div class="chart-card glass-card">
                <div class="chart-card-title">
                    <i data-lucide="wind" class="icon-primary"></i>
                    <span>Kanda Oksijen Düzeyi (SpO2)</span>
                </div>
                <div class="chart-container" id="spo2-chart-container">
                    <canvas id="spo2-chart"></canvas>
                </div>
            </div>

            <!-- 4. Sleep Stages Chart Card -->
            <div class="chart-card glass-card">
                <div class="chart-card-title">
                    <i data-lucide="moon" class="icon-purple"></i>
                    <span>Uyku Evreleri Grafiği</span>
                </div>
                <div class="chart-container-small" id="sleep-stages-container">
                    <canvas id="sleep-stages-chart"></canvas>
                </div>
            </div>
        </div>
    `;
}

/**
 * Render the empty state card when there's no data for the chosen date
 */
function renderEmptyState() {
    return `
        <div class="empty-state-card glass-card">
            <i data-lucide="database" class="empty-icon"></i>
            <h3>Saatlik Veri Bulunmuyor</h3>
            <p>Seçilen tarih için detaylı saatlik dilim verisi bulunamadı. Lütfen ana sayfa üzerinden Samsung Health PDF/JSON dosyası import edin veya örnek veri yükleyin.</p>
            <button class="btn btn-primary" onclick="window.location.hash = '#/'">Ana Sayfaya Git</button>
        </div>
    `;
}

/**
 * Renders a clean visual placeholder inside a chart container if data is missing
 */
function showNoChartDataPlaceholder(containerId, text) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.innerHTML = `
        <div class="chart-placeholder">
            <p>${text}</p>
        </div>
    `;
}

/**
 * Setup Event Listeners for Date navigation
 */
function setupEventListeners(container, currentDateStr) {
    container.querySelector('#btn-prev-day').addEventListener('click', () => {
        const d = new Date(currentDateStr);
        d.setDate(d.getDate() - 1);
        window.LongevityState.setDate(getLocalDateString(d));
        window.location.reload();
    });

    container.querySelector('#btn-next-day').addEventListener('click', () => {
        const d = new Date(currentDateStr);
        d.setDate(d.getDate() + 1);
        window.LongevityState.setDate(getLocalDateString(d));
        window.location.reload();
    });
}

// Helper: readable Date formatter
function formatDateReadable(dateStr) {
    const d = new Date(dateStr);
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    return d.toLocaleDateString('tr-TR', options);
}

// Helper: format YYYY-MM-DD
function getLocalDateString(date) {
    const offset = date.getTimezoneOffset();
    const localDate = new Date(date.getTime() - (offset * 60 * 1000));
    return localDate.toISOString().split('T')[0];
}
