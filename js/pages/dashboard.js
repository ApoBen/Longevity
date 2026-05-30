// Dashboard Page Controller - Longevity Platform
import { DB } from '../db.js';
import { DataImporter } from '../data-importer.js';
import { getDemoData } from '../utils/demo-data.js';

export async function render(container) {
    const selectedDate = window.LongevityState.selectedDate;
    
    // 1. Fetch data for selected date
    let healthData = await DB.get('health_data', selectedDate);
    
    // 2. Fetch active medications to show today's medication summary
    const medications = await DB.getAll('medications');
    const activeMeds = medications.filter(m => m.active);
    
    // Fetch medication logs for today
    const startOfDay = `${selectedDate}T00:00:00`;
    const endOfDay = `${selectedDate}T23:59:59`;
    let takenMedsCount = 0;
    
    for (const med of activeMeds) {
        const logs = await DB.getMedicationLogsByRange(med.id, startOfDay, endOfDay);
        if (logs.length > 0) {
            takenMedsCount++;
        }
    }

    // 3. Render HTML Layout
    container.innerHTML = `
        <div class="dashboard-container">
            <!-- Day Navigation Header -->
            <div class="date-navigator-container glass-card">
                <button id="btn-prev-day" class="btn btn-secondary btn-icon">
                    <i data-lucide="chevron-left"></i>
                </button>
                <div class="current-date-info">
                    <h2 id="current-date-display">${formatDateReadable(selectedDate)}</h2>
                    <p class="subtitle">Günlük Sağlık Durumu</p>
                </div>
                <button id="btn-next-day" class="btn btn-secondary btn-icon">
                    <i data-lucide="chevron-right"></i>
                </button>
            </div>

            ${healthData ? renderFullDashboard(healthData, activeMeds.length, takenMedsCount) : renderEmptyState()}

            <!-- Bottom Actions and Data Import Section -->
            <div class="dashboard-actions-card glass-card">
                <div class="actions-header">
                    <i data-lucide="settings" class="action-icon"></i>
                    <h3>Veri Yönetimi</h3>
                </div>
                <div class="actions-body">
                    <p class="action-desc">Samsung Health uygulamasından export ettiğiniz saatlik JSON dosyasını buraya yükleyin veya sistemi test etmek için örnek veriyi yükleyin.</p>
                    <div class="action-buttons-row">
                        <label class="btn btn-primary" id="lbl-import-json">
                            <i data-lucide="upload"></i> JSON Veri Yükle
                            <input type="file" id="input-import-json" accept=".json" style="display: none;">
                        </label>
                        <button class="btn btn-secondary" id="btn-load-demo">
                            <i data-lucide="beaker"></i> Demo Veri Yükle
                        </button>
                    </div>
                    <div class="last-sync-info">
                        <span id="last-sync-text">Son güncelleme: ${healthData ? 'Bugün ' + (healthData.exportDate ? formatTimeOnly(healthData.exportDate) : '') : 'Veri yüklenmedi'}</span>
                    </div>
                </div>
            </div>
        </div>
    `;

    // 4. Draw Sparklines if data exists
    if (healthData) {
        drawHeartRateSparkline(healthData);
        drawSpo2Sparkline(healthData);
        drawSleepProgressRing(healthData);
    }

    // 5. Setup Interactive Event Listeners
    setupEventListeners(container, selectedDate);
}

/**
 * Render the full dashboard grid when data exists
 */
function renderFullDashboard(data, totalMeds, takenMeds) {
    const hr = data.heartRate?.dailySummary || { avg: 0, min: 0, max: 0, resting: 0 };
    const spo2 = data.bloodOxygen || { hourly: [] };
    const steps = data.steps || { total: 0, goal: 10000 };
    const sleep = data.sleep || { totalMinutes: 0, score: 0 };
    const cal = data.calories || { total: 0, active: 0, rest: 0 };
    const energy = data.energyScore || 0;

    // Calculate SpO2 average
    let avgSpo2 = 0;
    let minSpo2 = 0;
    if (spo2.hourly && spo2.hourly.length > 0) {
        const valid = spo2.hourly.filter(h => h.avg > 0);
        if (valid.length > 0) {
            avgSpo2 = (valid.reduce((acc, curr) => acc + curr.avg, 0) / valid.length).toFixed(1);
            minSpo2 = Math.min(...valid.map(h => h.min));
        }
    }

    // Calculate Steps percentage
    const stepsPercent = Math.min(100, Math.round((steps.total / steps.goal) * 100));

    return `
        <div class="dashboard-grid">
            <!-- 1. HEART RATE CARD (❤️) -->
            <div class="dashboard-card glass-card glass-card-danger clickable-card" id="card-heart-rate">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-danger-soft">
                        <i data-lucide="heart" class="icon-danger"></i>
                    </span>
                    <span class="card-sparkline">
                        <canvas id="hr-sparkline" width="60" height="24"></canvas>
                    </span>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${hr.avg || '--'}</span>
                    <span class="card-unit">BPM</span>
                </div>
                <div class="card-title">Ortalama Nabız</div>
                <div class="card-footer-row">
                    <span>Min/Maks: <strong>${hr.min || '--'}-${hr.max || '--'}</strong></span>
                    <span>Dinlenme: <strong>${hr.resting || '--'}</strong></span>
                </div>
            </div>

            <!-- 2. BLOOD OXYGEN CARD (🫁) -->
            <div class="dashboard-card glass-card glass-card-primary clickable-card" id="card-spo2">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-primary-soft">
                        <i data-lucide="wind" class="icon-primary"></i>
                    </span>
                    <span class="card-sparkline">
                        <canvas id="spo2-sparkline" width="60" height="24"></canvas>
                    </span>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${avgSpo2 || '--'}</span>
                    <span class="card-unit">%</span>
                </div>
                <div class="card-title">Kanda Oksijen (SpO2)</div>
                <div class="card-footer-row">
                    <span>En Düşük: <strong>${minSpo2 || '--'}%</strong></span>
                    <span>Ölçüm: <strong>${spo2.hourly ? spo2.hourly.length : 0}</strong></span>
                </div>
            </div>

            <!-- 3. ENERGY SCORE CARD (⚡) -->
            <div class="dashboard-card glass-card glass-card-warning" id="card-energy">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-warning-soft">
                        <i data-lucide="zap" class="icon-warning"></i>
                    </span>
                    <span class="card-badge badge badge-warning">${energy >= 80 ? 'Harika' : energy >= 60 ? 'İyi' : 'Yorgun'}</span>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${energy || '--'}</span>
                    <span class="card-unit">/100</span>
                </div>
                <div class="card-title">Enerji Skoru</div>
                <div class="card-footer-row">
                    <div class="energy-progress-container">
                        <div class="progress-bar-container">
                            <div class="progress-bar-fill" style="width: ${energy}%; background: var(--color-warning);"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 4. STEPS CARD (🚶) -->
            <div class="dashboard-card glass-card glass-card-success clickable-card" id="card-steps">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-success-soft">
                        <i data-lucide="footprints" class="icon-success"></i>
                    </span>
                    <span class="card-badge badge badge-success">${stepsPercent}%</span>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${formatNumber(steps.total)}</span>
                    <span class="card-unit">/ ${formatNumber(steps.goal)} adım</span>
                </div>
                <div class="card-title">Günlük Adım Sayısı</div>
                <div class="card-footer-row" style="flex-direction: column; gap: var(--space-2); align-items: stretch;">
                    <div class="progress-bar-container">
                        <div class="progress-bar-fill" style="width: ${stepsPercent}%; background: var(--color-success);"></div>
                    </div>
                    <div style="display:flex; justify-content: space-between; font-size: var(--font-xs); color: var(--text-muted);">
                        <span>Mesafe: <strong>${(steps.distanceMeters / 1000).toFixed(2)} km</strong></span>
                        <span>Hedef: <strong>${formatNumber(steps.goal)}</strong></span>
                    </div>
                </div>
            </div>

            <!-- 5. SLEEP CARD (😴) -->
            <div class="dashboard-card glass-card glass-card-purple clickable-card" id="card-sleep">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-purple-soft">
                        <i data-lucide="moon" class="icon-purple"></i>
                    </span>
                    <div class="progress-ring" id="sleep-ring" style="width: 32px; height: 32px;"></div>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${formatMinutesToHours(sleep.totalMinutes)}</span>
                </div>
                <div class="card-title">Uyku Süresi</div>
                <div class="card-footer-row">
                    <span>Uyku Skoru: <strong>${sleep.score || '--'} / 100</strong></span>
                    <span>Derin Uyku: <strong>${sleep.stages ? formatMinutesToHours(sleep.stages.deep) : '--'}</strong></span>
                </div>
            </div>

            <!-- 6. CALORIES CARD (🔥) -->
            <div class="dashboard-card glass-card glass-card-teal" id="card-calories">
                <div class="card-header-row">
                    <span class="card-icon-bg bg-teal-soft">
                        <i data-lucide="flame" class="icon-teal"></i>
                    </span>
                    <span class="card-badge badge badge-primary">${Math.round(cal.total)} kcal</span>
                </div>
                <div class="card-value-container">
                    <span class="card-value">${Math.round(cal.active) || '--'}</span>
                    <span class="card-unit">aktif kcal</span>
                </div>
                <div class="card-title">Kalori Yakımı</div>
                <div class="card-footer-row">
                    <span>Dinlenme: <strong>${Math.round(cal.rest) || '--'} kcal</strong></span>
                    <span>Toplam: <strong>${Math.round(cal.total) || '--'} kcal</strong></span>
                </div>
            </div>
        </div>

        <!-- Today's Medication Mini Summary -->
        <div class="dashboard-card-full glass-card glass-card-warning clickable-card" id="card-meds-summary" style="margin-bottom: var(--space-6);">
            <div class="med-summary-row">
                <div class="med-summary-left">
                    <span class="card-icon-bg bg-warning-soft">
                        <i data-lucide="pill" class="icon-warning"></i>
                    </span>
                    <div class="med-summary-text">
                        <h4>Bugünkü İlaç Takibi</h4>
                        <p class="subtitle">${totalMeds > 0 ? `${takenMeds} / ${totalMeds} ilaç alındı` : 'Aktif ilaç bulunmuyor'}</p>
                    </div>
                </div>
                <div class="med-summary-right">
                    ${totalMeds > 0 ? `
                        <div class="progress-bar-container" style="width: 100px;">
                            <div class="progress-bar-fill" style="width: ${Math.round((takenMeds / totalMeds) * 100)}%; background: var(--color-warning);"></div>
                        </div>
                        <span class="med-percentage">${Math.round((takenMeds / totalMeds) * 100)}%</span>
                    ` : `
                        <button class="btn btn-secondary btn-sm" id="btn-add-med-now">İlaç Ekle</button>
                    `}
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
            <h3>Sağlık Verisi Bulunmuyor</h3>
            <p>Seçilen tarih için Samsung Health verisi bulunamadı. Lütfen üstteki veya alttaki butonları kullanarak bir JSON dosyası import edin ya da hızlıca denemek için "Demo Veri Yükle" butonuna basın.</p>
        </div>
    `;
}

/**
 * Setup Event Listeners for Date navigation, JSON import and demo data
 */
function setupEventListeners(container, currentDateStr) {
    // 1. Date Navigation Buttons
    container.querySelector('#btn-prev-day').addEventListener('click', () => {
        const d = new Date(currentDateStr);
        d.setDate(d.getDate() - 1);
        window.LongevityState.setDate(getLocalDateString(d));
        window.location.reload(); // Refresh the page to render new date
    });

    container.querySelector('#btn-next-day').addEventListener('click', () => {
        const d = new Date(currentDateStr);
        d.setDate(d.getDate() + 1);
        window.LongevityState.setDate(getLocalDateString(d));
        window.location.reload();
    });

    // 2. Clickable Cards routing
    const hrCard = container.querySelector('#card-heart-rate');
    if (hrCard) hrCard.addEventListener('click', () => window.location.hash = '#/timeline');

    const spo2Card = container.querySelector('#card-spo2');
    if (spo2Card) spo2Card.addEventListener('click', () => window.location.hash = '#/timeline');

    const stepsCard = container.querySelector('#card-steps');
    if (stepsCard) stepsCard.addEventListener('click', () => window.location.hash = '#/timeline');

    const sleepCard = container.querySelector('#card-sleep');
    if (sleepCard) sleepCard.addEventListener('click', () => window.location.hash = '#/timeline');

    const medsSummaryCard = container.querySelector('#card-meds-summary');
    if (medsSummaryCard) medsSummaryCard.addEventListener('click', () => window.location.hash = '#/medications');

    const addMedBtn = container.querySelector('#btn-add-med-now');
    if (addMedBtn) addMedBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        window.location.hash = '#/medications';
    });

    // 3. Demo Data Loader
    container.querySelector('#btn-load-demo').addEventListener('click', async () => {
        try {
            const demo = getDemoData();
            let count = 0;
            for (const day of demo.days) {
                await DB.put('health_data', day);
                count++;
            }
            window.showToast(`Örnek veri başarıyla yüklendi (${count} gün)!`, 'success');
            setTimeout(() => window.location.reload(), 1000);
        } catch (e) {
            window.showToast('Demo veri yüklenirken hata oluştu.', 'danger');
        }
    });

    // 4. File input json importer
    const fileInput = container.querySelector('#input-import-json');
    fileInput.addEventListener('change', async (event) => {
        const file = event.target.files[0];
        if (!file) return;

        try {
            window.showToast('Dosya işleniyor...', 'info');
            const result = await DataImporter.importFile(file);
            if (result.success) {
                window.showToast(`Başarıyla içe aktarıldı: ${result.count} gün verisi yüklendi.`, 'success');
                setTimeout(() => window.location.reload(), 1000);
            }
        } catch (error) {
            window.showToast(error.message, 'danger');
        }
    });
}

/**
 * Draws Heart Rate Sparkline using Canvas 2D
 */
function drawHeartRateSparkline(data) {
    const canvas = document.getElementById('hr-sparkline');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const hourly = data.heartRate?.hourly || [];
    if (hourly.length === 0) return;

    // Filter valid averages and draw
    const points = hourly.map(h => h.avg).filter(val => val > 0);
    if (points.length < 2) return;

    drawSparklineCanvas(ctx, canvas.width, canvas.height, points, '#ef4444');
}

/**
 * Draws SpO2 Sparkline using Canvas 2D
 */
function drawSpo2Sparkline(data) {
    const canvas = document.getElementById('spo2-sparkline');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const hourly = data.bloodOxygen?.hourly || [];
    if (hourly.length === 0) return;

    const points = hourly.map(h => h.avg).filter(val => val > 0);
    if (points.length < 2) return;

    drawSparklineCanvas(ctx, canvas.width, canvas.height, points, '#3b82f6');
}

/**
 * Base Sparkline Drawing Logic
 */
function drawSparklineCanvas(ctx, width, height, points, strokeColor) {
    ctx.clearRect(0, 0, width, height);

    const min = Math.min(...points);
    const max = Math.max(...points);
    const range = max - min === 0 ? 1 : max - min;

    ctx.beginPath();
    ctx.strokeStyle = strokeColor;
    ctx.lineWidth = 1.5;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';

    points.forEach((val, i) => {
        const x = (i / (points.length - 1)) * (width - 4) + 2;
        const y = height - ((val - min) / range) * (height - 6) - 3;
        if (i === 0) {
            ctx.moveTo(x, y);
        } else {
            ctx.lineTo(x, y);
        }
    });
    ctx.stroke();

    // Fill under line
    ctx.lineTo((width - 4) + 2, height);
    ctx.lineTo(2, height);
    ctx.closePath();
    ctx.fillStyle = strokeColor === '#ef4444' ? 'rgba(239, 68, 68, 0.08)' : 'rgba(59, 130, 246, 0.08)';
    ctx.fill();
}

/**
 * Draw Sleep Progress Ring
 */
function drawSleepProgressRing(data) {
    const container = document.getElementById('sleep-ring');
    if (!container) return;

    const score = data.sleep?.score || 0;
    const radius = 14;
    const circumference = 2 * Math.PI * radius;
    const strokeDashoffset = circumference - (score / 100) * circumference;

    container.innerHTML = `
        <svg width="32" height="32">
            <circle class="progress-ring-bg" cx="16" cy="16" r="${radius}" stroke="rgba(255, 255, 255, 0.05)" stroke-width="2.5"></circle>
            <circle class="progress-ring-fill" cx="16" cy="16" r="${radius}" stroke="var(--color-purple)" stroke-width="2.5" 
                stroke-dasharray="${circumference}" stroke-dashoffset="${strokeDashoffset}"></circle>
        </svg>
        <span class="progress-ring-text" style="font-size: 8px; color: var(--color-purple); font-weight: bold;">${score}%</span>
    `;
}

// Helper: readable Date formatter (e.g. 30 Mayıs 2026)
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

// Helper: minutes to Hours/Minutes string
function formatMinutesToHours(totalMinutes) {
    if (!totalMinutes) return '--';
    const hours = Math.floor(totalMinutes / 60);
    const mins = totalMinutes % 60;
    return hours > 0 ? `${hours}s ${mins}dk` : `${mins}dk`;
}

// Helper: time format YYYY-MM-DDTHH:MM:SS -> HH:MM
function formatTimeOnly(isoStr) {
    if (!isoStr) return '';
    try {
        const d = new Date(isoStr);
        return d.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
    } catch (e) {
        return '';
    }
}

// Helper: thousand separator
function formatNumber(num) {
    if (!num) return '0';
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
