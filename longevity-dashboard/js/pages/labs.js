import { DB } from '../db.js';
import { parseEnabizPdf } from '../pdf-parser.js';

let trendChartInstance = null;

export async function render(container) {
    container.innerHTML = `
        <div class="labs-page-container">
            <div class="welcome-header glass-card" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                <div>
                    <h2>Tahlil Sonuçları</h2>
                    <p style="opacity: 0.8; font-size: 0.9rem; margin-top: 4px;">E-nabız PDF tahlillerinizi yükleyin, zaman içindeki trendleri izleyin.</p>
                </div>
                <div style="display: flex; gap: 12px;">
                    <input type="file" id="pdf-upload" accept=".pdf" style="display: none;">
                    <button id="upload-pdf-btn" class="btn btn-primary">
                        <i data-lucide="upload-cloud"></i> PDF Yükle
                    </button>
                </div>
            </div>

            <div class="labs-content" style="display: grid; grid-template-columns: 1fr 3fr; gap: 24px;">
                <!-- Left Sidebar: Select Parameter -->
                <div class="glass-card" style="height: fit-content;">
                    <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Parametreler</h3>
                    <div id="parameter-list" style="display: flex; flex-direction: column; gap: 4px; max-height: 500px; overflow-y: auto;">
                        <!-- List of unique parameters -->
                    </div>
                </div>

                <!-- Right Area: Chart and History -->
                <div style="display: flex; flex-direction: column; gap: 24px;">
                    <div class="glass-card chart-container" style="height: 350px; position: relative;">
                        <canvas id="lab-trend-chart"></canvas>
                    </div>

                    <div class="glass-card">
                        <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;" id="history-title">Tahlil Geçmişi</h3>
                        <div id="lab-history-table" style="overflow-x: auto;">
                            <!-- Table of history -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Upload Confirmation Dialog -->
        <dialog id="lab-confirm-dialog" class="glass-card" style="padding: 24px; border: 1px solid rgba(255,255,255,0.1); border-radius: 16px; background: rgba(15, 23, 42, 0.95); color: white; width: 90%; max-width: 600px; backdrop-filter: blur(16px);">
            <div class="dialog-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <h3 style="margin: 0;">Sonuçları Onayla</h3>
                <button id="close-lab-dialog" class="btn-icon" style="background: transparent; border: none; color: white; cursor: pointer;"><i data-lucide="x"></i></button>
            </div>
            <div style="margin-bottom: 16px;">
                <label class="form-label">Tahlil Tarihi</label>
                <input type="date" id="lab-date" class="input" style="margin-top: 8px;" required>
            </div>
            <div id="parsed-results" style="max-height: 300px; overflow-y: auto; margin-bottom: 16px; background: rgba(0,0,0,0.2); border-radius: 8px; padding: 8px;">
                <!-- Extracted parameters preview -->
            </div>
            <div class="dialog-actions" style="display: flex; justify-content: flex-end; gap: 12px;">
                <button type="button" class="btn btn-secondary" id="cancel-lab-dialog">İptal</button>
                <button type="button" class="btn btn-primary" id="save-lab-results">Kaydet</button>
            </div>
        </dialog>
    `;

    lucide.createIcons();

    let currentParsedData = [];

    const uploadInput = document.getElementById('pdf-upload');
    const uploadBtn = document.getElementById('upload-pdf-btn');
    const dialog = document.getElementById('lab-confirm-dialog');
    const closeBtn = document.getElementById('close-lab-dialog');
    const cancelBtn = document.getElementById('cancel-lab-dialog');
    const saveBtn = document.getElementById('save-lab-results');
    const dateInput = document.getElementById('lab-date');
    const resultsContainer = document.getElementById('parsed-results');

    // Default to today
    dateInput.value = new Date().toISOString().split('T')[0];

    uploadBtn.addEventListener('click', () => uploadInput.click());

    uploadInput.addEventListener('change', async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        try {
            uploadBtn.innerHTML = '<i class="lucide-loader" style="animation: spin 1s linear infinite;"></i> İşleniyor...';
            currentParsedData = await parseEnabizPdf(file);
            
            if (currentParsedData.length === 0) {
                alert('PDF dosyasından hiç tahlil sonucu okunamadı.');
                uploadInput.value = '';
                uploadBtn.innerHTML = '<i data-lucide="upload-cloud"></i> PDF Yükle';
                lucide.createIcons();
                return;
            }

            // Show preview
            resultsContainer.innerHTML = currentParsedData.map(item => \`
                <div style="display: flex; justify-content: space-between; padding: 4px 8px; border-bottom: 1px solid rgba(255,255,255,0.05);">
                    <span style="font-weight: 500;">\${item.parameter}</span>
                    <span><strong style="color: \${isOutOfRange(item.value, item.reference) ? '#ef4444' : '#4ade80'}">\${item.value}</strong> \${item.unit}</span>
                </div>
            \`).join('');

            dialog.showModal();
        } catch (err) {
            console.error(err);
            alert('PDF okunurken bir hata oluştu: ' + err.message);
        } finally {
            uploadBtn.innerHTML = '<i data-lucide="upload-cloud"></i> PDF Yükle';
            lucide.createIcons();
            uploadInput.value = '';
        }
    });

    const closeDialog = () => dialog.close();
    closeBtn.addEventListener('click', closeDialog);
    cancelBtn.addEventListener('click', closeDialog);

    saveBtn.addEventListener('click', async () => {
        const date = dateInput.value;
        if (!date) {
            alert('Lütfen bir tarih seçin.');
            return;
        }

        const report = {
            id: 'lab_' + Date.now(),
            date: date,
            results: currentParsedData,
            createdAt: new Date().toISOString()
        };

        await DB.put('lab_reports', report);
        dialog.close();
        await loadLabData();
    });

    await loadLabData();
}

function isOutOfRange(valueStr, refStr) {
    if (!refStr) return false;
    const val = parseFloat(valueStr.replace(',', '.'));
    if (isNaN(val)) return false;

    // Handle ranges like "4.5 - 11.2" or "65 - 175"
    const match = refStr.match(/([0-9.,]+)\s*-\s*([0-9.,]+)/);
    if (match) {
        const min = parseFloat(match[1].replace(',', '.'));
        const max = parseFloat(match[2].replace(',', '.'));
        if (val < min || val > max) return true;
    }
    return false;
}

async function loadLabData() {
    const reports = await DB.getAll('lab_reports');
    
    // Sort reports by date ascending
    reports.sort((a, b) => new Date(a.date) - new Date(b.date));

    // Extract unique parameters
    const paramSet = new Set();
    reports.forEach(r => {
        r.results.forEach(res => paramSet.add(res.parameter));
    });

    const parameters = Array.from(paramSet).sort();
    
    const paramList = document.getElementById('parameter-list');
    
    if (parameters.length === 0) {
        paramList.innerHTML = '<p style="padding: 16px; opacity: 0.5;">Henüz veri yok.</p>';
        document.getElementById('lab-history-table').innerHTML = '<p style="padding: 16px; opacity: 0.5;">Lütfen bir E-nabız PDF dosyası yükleyin.</p>';
        return;
    }

    paramList.innerHTML = parameters.map((p, index) => \`
        <button class="param-btn" data-param="\${p}" style="text-align: left; background: \${index === 0 ? 'rgba(59, 130, 246, 0.2)' : 'transparent'}; border: none; padding: 8px 12px; color: white; cursor: pointer; border-radius: 4px; transition: background 0.2s;">
            \${p}
        </button>
    \`).join('');

    // Attach click events
    document.querySelectorAll('.param-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            document.querySelectorAll('.param-btn').forEach(b => b.style.background = 'transparent');
            e.currentTarget.style.background = 'rgba(59, 130, 246, 0.2)';
            const param = e.currentTarget.dataset.param;
            renderChartAndHistory(reports, param);
        });
    });

    // Render first param by default
    if (parameters.length > 0) {
        renderChartAndHistory(reports, parameters[0]);
    }
}

function renderChartAndHistory(reports, parameterName) {
    document.getElementById('history-title').textContent = \`\${parameterName} Geçmişi\`;
    
    const historyData = [];
    
    reports.forEach(report => {
        const item = report.results.find(r => r.parameter === parameterName);
        if (item) {
            historyData.push({
                date: report.date,
                value: parseFloat(item.value.replace(',', '.')),
                valueStr: item.value,
                unit: item.unit,
                reference: item.reference
            });
        }
    });

    // Render Table
    const tableHTML = \`
        <table style="width: 100%; border-collapse: collapse; text-align: left;">
            <thead>
                <tr style="border-bottom: 1px solid rgba(255,255,255,0.1);">
                    <th style="padding: 8px; color: rgba(255,255,255,0.6); font-weight: 500;">Tarih</th>
                    <th style="padding: 8px; color: rgba(255,255,255,0.6); font-weight: 500;">Sonuç</th>
                    <th style="padding: 8px; color: rgba(255,255,255,0.6); font-weight: 500;">Referans</th>
                </tr>
            </thead>
            <tbody>
                \${historyData.map(d => {
                    const outOfRange = isOutOfRange(d.valueStr, d.reference);
                    return \`
                        <tr style="border-bottom: 1px solid rgba(255,255,255,0.05);">
                            <td style="padding: 12px 8px;">\${new Date(d.date).toLocaleDateString('tr-TR')}</td>
                            <td style="padding: 12px 8px; font-weight: \${outOfRange ? 'bold' : 'normal'}; color: \${outOfRange ? '#ef4444' : '#4ade80'}">\${d.valueStr} \${d.unit}</td>
                            <td style="padding: 12px 8px; opacity: 0.7;">\${d.reference}</td>
                        </tr>
                    \`;
                }).reverse().join('')}
            </tbody>
        </table>
    \`;
    document.getElementById('lab-history-table').innerHTML = tableHTML;

    // Render Chart
    const ctx = document.getElementById('lab-trend-chart');
    if (!ctx) return;

    if (trendChartInstance) {
        trendChartInstance.destroy();
    }

    const labels = historyData.map(d => new Date(d.date).toLocaleDateString('tr-TR'));
    const dataPoints = historyData.map(d => d.value);
    
    // Extract reference range for bands
    let refMin = null;
    let refMax = null;
    if (historyData.length > 0 && historyData[historyData.length - 1].reference) {
        const match = historyData[historyData.length - 1].reference.match(/([0-9.,]+)\s*-\s*([0-9.,]+)/);
        if (match) {
            refMin = parseFloat(match[1].replace(',', '.'));
            refMax = parseFloat(match[2].replace(',', '.'));
        }
    }

    const annotations = {};
    if (refMin !== null && refMax !== null) {
        annotations.box1 = {
            type: 'box',
            yMin: refMin,
            yMax: refMax,
            backgroundColor: 'rgba(74, 222, 128, 0.1)',
            borderWidth: 0,
            drawTime: 'beforeDatasetsDraw'
        };
    }

    trendChartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: parameterName,
                data: dataPoints,
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.2)',
                borderWidth: 2,
                pointBackgroundColor: dataPoints.map(val => {
                    if (refMin !== null && refMax !== null) {
                        return (val < refMin || val > refMax) ? '#ef4444' : '#4ade80';
                    }
                    return '#3b82f6';
                }),
                pointRadius: 5,
                pointHoverRadius: 7,
                fill: false,
                tension: 0.2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false },
                annotation: { annotations }
            },
            scales: {
                y: {
                    grid: { color: 'rgba(255, 255, 255, 0.1)' },
                    ticks: { color: 'rgba(255, 255, 255, 0.6)' }
                },
                x: {
                    grid: { display: false },
                    ticks: { color: 'rgba(255, 255, 255, 0.6)' }
                }
            }
        }
    });
}
