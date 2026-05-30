import { DB } from '../db.js';
import { renderPharmaChart, updatePharmaChart } from '../charts/pharma-curve.js';

export async function render(container) {
    container.innerHTML = `
        <div class="medications-page-container">
            <div class="welcome-header glass-card" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <div>
                    <h2>İlaç Takibi</h2>
                    <p style="opacity: 0.8; font-size: 0.9rem; margin-top: 4px;">İlaçlarınızı ekleyin, kandaki plazma konsantrasyonlarını takip edin.</p>
                </div>
                <button id="add-medication-btn" class="btn btn-primary">
                    <i data-lucide="plus"></i> Yeni İlaç
                </button>
            </div>

            <div class="chart-container glass-card" style="margin-bottom: 24px; position: relative; height: 300px;">
                <canvas id="pharma-chart"></canvas>
            </div>

            <div id="medications-list" class="medications-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 16px;">
                <!-- Medication cards will be rendered here -->
            </div>
        </div>

        <dialog id="medication-dialog" class="glass-card" style="padding: 24px; border: 1px solid rgba(255,255,255,0.1); border-radius: 16px; background: rgba(15, 23, 42, 0.95); color: white; width: 90%; max-width: 400px; backdrop-filter: blur(16px);">
            <div class="dialog-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                <h3 style="margin: 0;">Yeni İlaç Ekle</h3>
                <button id="close-dialog-btn" class="btn-icon" style="background: transparent; border: none; color: white; cursor: pointer;"><i data-lucide="x"></i></button>
            </div>
            <form id="medication-form" class="dialog-form" style="display: flex; flex-direction: column; gap: 16px;">
                <div class="form-group" style="margin: 0;">
                    <label for="med-name" class="form-label">İlaç Adı</label>
                    <input type="text" id="med-name" class="input" required placeholder="Örn: Parol, D Vitamini">
                </div>
                <div class="form-row" style="display: flex; gap: 16px;">
                    <div class="form-group" style="flex: 1; margin: 0;">
                        <label for="med-dose" class="form-label">Doz (mg)</label>
                        <input type="text" id="med-dose" class="input" required placeholder="Örn: 500">
                    </div>
                </div>
                <div class="form-row" style="display: flex; gap: 16px;">
                    <div class="form-group" style="flex: 1; margin: 0;">
                        <label for="med-times" class="form-label">Günlük Adet</label>
                        <input type="number" id="med-times" class="input" required min="1" max="10" value="1">
                    </div>
                    <div class="form-group" style="flex: 1; margin: 0;">
                        <label for="med-halflife" class="form-label">Yarı Ömür (Saat)</label>
                        <input type="number" id="med-halflife" class="input" required step="0.1" min="0.1" placeholder="Örn: 4">
                    </div>
                </div>
                <div class="form-group" style="margin: 0;">
                    <label for="med-color" class="form-label">Grafik Rengi</label>
                    <input type="color" id="med-color" class="input" value="#3b82f6" style="padding: 4px; height: 40px; cursor: pointer;">
                </div>
                <div class="dialog-actions" style="display: flex; justify-content: flex-end; gap: 12px; margin-top: 16px;">
                    <button type="button" class="btn btn-secondary" id="cancel-dialog-btn">İptal</button>
                    <button type="submit" class="btn btn-primary">Kaydet</button>
                </div>
            </form>
        </dialog>
    `;

    lucide.createIcons();

    const dialog = document.getElementById('medication-dialog');
    const addBtn = document.getElementById('add-medication-btn');
    const closeBtn = document.getElementById('close-dialog-btn');
    const cancelBtn = document.getElementById('cancel-dialog-btn');
    const form = document.getElementById('medication-form');
    
    // Add ::backdrop styling dynamically for native dialog
    if (!document.getElementById('dialog-backdrop-style')) {
        const style = document.createElement('style');
        style.id = 'dialog-backdrop-style';
        style.textContent = \`
            dialog::backdrop {
                background: rgba(3, 4, 9, 0.75);
                backdrop-filter: blur(4px);
            }
        \`;
        document.head.appendChild(style);
    }

    addBtn.addEventListener('click', () => {
        form.reset();
        dialog.showModal();
    });

    const closeDialog = () => dialog.close();
    closeBtn.addEventListener('click', closeDialog);
    cancelBtn.addEventListener('click', closeDialog);

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const medication = {
            id: 'med_' + Date.now(),
            name: document.getElementById('med-name').value,
            dose: document.getElementById('med-dose').value,
            timesPerDay: parseInt(document.getElementById('med-times').value) || 1,
            halfLife: parseFloat(document.getElementById('med-halflife').value),
            color: document.getElementById('med-color').value,
            active: 1,
            createdAt: new Date().toISOString()
        };

        await DB.put('medications', medication);
        dialog.close();
        await loadMedications();
        await updatePharmaChart();
    });

    await loadMedications();
    await renderPharmaChart('pharma-chart');
}

async function loadMedications() {
    const listContainer = document.getElementById('medications-list');
    if (!listContainer) return;

    try {
        const meds = await DB.getByIndex('medications', 'active', 1);
        
        if (meds.length === 0) {
            listContainer.innerHTML = \`
            listContainer.innerHTML = `
                <div class="empty-state glass-card" style="grid-column: 1 / -1; text-align: center; padding: 48px;">
                    <i data-lucide="pill" style="width: 48px; height: 48px; opacity: 0.5; margin-bottom: 16px; display: inline-block;"></i>
                    <h3 style="margin-bottom: 8px;">Henüz ilaç eklemediniz</h3>
                    <p style="opacity: 0.7;">Takip etmek istediğiniz ilaçları ekleyerek başlayın.</p>
                </div>
            `;
            lucide.createIcons();
            return;
        }
        const todayStart = new Date();
        todayStart.setHours(0,0,0,0);
        const logs = await DB.getAll('medication_logs');

        listContainer.innerHTML = meds.map(med => {
            const medLogs = logs.filter(l => l.medicationId === med.id && new Date(l.timestamp) >= todayStart);
            const takenCount = medLogs.length;
            const target = med.timesPerDay || 1;
            
            // Generate progress string ●●○
            let progressStr = '';
            for(let i=0; i<target; i++) {
                progressStr += i < takenCount ? '●' : '○';
            }
            if (takenCount > target) {
                progressStr += ' (Fazla doz!)';
            }

            return `
            <div class="medication-card glass-card" style="border-left: 4px solid ${med.color}; padding: 20px; display: flex; flex-direction: column; justify-content: space-between;">
                <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                    <div>
                        <h3 style="margin: 0 0 8px 0; font-size: 1.2rem;">${med.name}</h3>
                        <span class="badge" style="background: rgba(255,255,255,0.1); padding: 4px 8px; border-radius: 4px; font-size: 0.8rem;">${med.dose} mg</span>
                    </div>
                    <button class="btn-icon delete-btn" data-id="${med.id}" style="background: transparent; border: none; color: #ef4444; opacity: 0.5; cursor: pointer; padding: 4px;">
                        <i data-lucide="trash-2" style="width: 16px; height: 16px;"></i>
                    </button>
                </div>
                
                <div style="margin-top: 16px; opacity: 0.8; font-size: 0.9rem; display: flex; flex-direction: column; gap: 6px;">
                    <div><i data-lucide="clock" style="width: 14px; height: 14px; display: inline-block;"></i> Yarı Ömür: ${med.halfLife} saat</div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <span style="letter-spacing: 2px; color: ${med.color}; font-size: 1.1rem;">${progressStr}</span>
                        <span style="font-size: 0.8rem;">(${takenCount}/${target} alındı)</span>
                    </div>
                </div>

                <div class="medication-actions" style="margin-top: 20px; display: flex; gap: 8px;">
                    <button class="btn btn-secondary log-btn" data-id="${med.id}" style="flex: 2; display: flex; justify-content: center; align-items: center; gap: 6px; background: rgba(34, 197, 94, 0.1); color: #4ade80; border: 1px solid rgba(34, 197, 94, 0.2);">
                        <i data-lucide="plus" style="width: 18px; height: 18px;"></i> Ekle
                    </button>
                    <button class="btn btn-secondary undo-btn" data-id="${med.id}" style="flex: 1; display: flex; justify-content: center; align-items: center; color: #94a3b8; border: 1px solid rgba(255, 255, 255, 0.1); background: rgba(0,0,0,0.2);">
                        <i data-lucide="minus" style="width: 18px; height: 18px;"></i> Geri Al
                    </button>
                </div>
            </div>
            `;
        }).join('');

        lucide.createIcons();

        // Attach log handlers (Phase 8 preparation)
        document.querySelectorAll('.log-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const id = e.currentTarget.dataset.id;
                await logMedicationIntake(id);
                await loadMedications();
            });
        });

        // Attach undo handlers
        document.querySelectorAll('.undo-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const id = e.currentTarget.dataset.id;
                await undoMedicationIntake(id);
                await loadMedications();
            });
        });

        // Attach delete handlers
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                if (confirm('Bu ilacı silmek istediğinize emin misiniz?')) {
                    const id = e.currentTarget.dataset.id;
                    await DB.delete('medications', id);
                    await loadMedications();
                    await updatePharmaChart();
                }
            });
        });

    } catch (error) {
        console.error('Error loading medications:', error);
        listContainer.innerHTML = '<div class="error glass-card" style="grid-column: 1 / -1; padding: 24px; color: #ef4444; text-align: center;">İlaçlar yüklenirken bir hata oluştu.</div>';
    }
}

async function logMedicationIntake(medicationId) {
    const logEntry = {
        id: 'log_' + Date.now(),
        medicationId: medicationId,
        timestamp: new Date().toISOString()
    };
    
    await DB.put('medication_logs', logEntry);
    
    // Show a small toast notification
    showToast('İlaç alımı kaydedildi', 'success');
    await updatePharmaChart();
}

async function undoMedicationIntake(medicationId) {
    const logs = await DB.getAll('medication_logs');
    const medLogs = logs.filter(l => l.medicationId === medicationId)
                        .sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
    
    if (medLogs.length > 0) {
        const lastLog = medLogs[0];
        await DB.delete('medication_logs', lastLog.id);
        showToast('Son alım geri alındı', 'primary');
        await updatePharmaChart();
    } else {
        alert('Geri alınacak kayıt bulunamadı.');
    }
}

function showToast(message, type = 'success') {
    // Basic toast implementation
    const toast = document.createElement('div');
    toast.className = \`glass-card toast-\${type}\`;
    toast.style.cssText = \`
        position: fixed;
        bottom: 80px; /* Above nav bar */
        left: 50%;
        transform: translateX(-50%);
        padding: 12px 24px;
        border-radius: 30px;
        background: \${type === 'success' ? 'rgba(34, 197, 94, 0.9)' : 'rgba(59, 130, 246, 0.9)'};
        color: white;
        z-index: 10000;
        font-weight: 500;
        box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        animation: fadeInOut 3s forwards;
    \`;
    toast.textContent = message;
    
    if (!document.getElementById('toast-keyframes')) {
        const style = document.createElement('style');
        style.id = 'toast-keyframes';
        style.textContent = \`
            @keyframes fadeInOut {
                0% { opacity: 0; transform: translate(-50%, 20px); }
                10% { opacity: 1; transform: translate(-50%, 0); }
                90% { opacity: 1; transform: translate(-50%, 0); }
                100% { opacity: 0; transform: translate(-50%, -20px); }
            }
        \`;
        document.head.appendChild(style);
    }
    
    document.body.appendChild(toast);
    setTimeout(() => {
        toast.remove();
    }, 3000);
}
