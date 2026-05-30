import { DB } from '../db.js';
import { renderVolumeChart } from '../charts/volume-chart.js';
import { render1RmRadarChart } from '../charts/one-rm-radar.js';
import { calculateRecovery, checkWorkoutConflict } from '../models/recovery.js';
import { renderRecoveryMap } from '../charts/recovery-map.js';

export async function render(container) {
    container.innerHTML = `
        <div class="workouts-page-container">
            <div class="welcome-header glass-card" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                <div>
                    <h2>İdman Takibi</h2>
                    <p style="opacity: 0.8; font-size: 0.9rem; margin-top: 4px;">Antrenmanlarınızı loglayın, gelişiminizi izleyin.</p>
                </div>
                <button id="start-workout-btn" class="btn btn-primary">
                    <i data-lucide="play"></i> İdman Başlat
                </button>
            </div>

            <div class="workouts-content" style="display: grid; grid-template-columns: 2fr 1fr; gap: 24px;">
                <!-- Workout History -->
                <div class="glass-card">
                    <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">İdman Geçmişi</h3>
                    <div id="workout-history-list" style="display: flex; flex-direction: column; gap: 12px;">
                        <!-- List of previous workouts -->
                    </div>
                </div>

                <!-- Metrics / Radar -->
                <div style="display: flex; flex-direction: column; gap: 24px;">
                    <div class="glass-card" style="height: fit-content;">
                        <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Toparlanma Durumu</h3>
                        <div id="recovery-map-container"></div>
                    </div>
                    
                    <div class="glass-card" style="height: fit-content;">
                        <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Haftalık Tonaj</h3>
                        <div class="chart-container" style="height: 200px; position: relative;">
                            <canvas id="volume-chart"></canvas>
                        </div>
                    </div>
                    
                    <div class="glass-card" style="height: fit-content;">
                        <h3 style="margin-bottom: 16px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">1RM Radar</h3>
                        <div class="chart-container" style="height: 250px; position: relative;">
                            <canvas id="radar-chart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Start Workout Dialog -->
        <dialog id="workout-dialog" class="glass-card" style="padding: 24px; border: 1px solid rgba(255,255,255,0.1); border-radius: 16px; background: rgba(15, 23, 42, 0.95); color: white; width: 95%; max-width: 700px; backdrop-filter: blur(16px);">
            <div class="dialog-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
                <h3 style="margin: 0;" id="workout-dialog-title">Yeni İdman</h3>
                <button id="close-workout-dialog" class="btn-icon" style="background: transparent; border: none; color: white; cursor: pointer;"><i data-lucide="x"></i></button>
            </div>
            
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 16px;">
                <div>
                    <label class="form-label">Tarih</label>
                    <input type="date" id="workout-date" class="input" required>
                </div>
                <div>
                    <label class="form-label">İdman Adı</label>
                    <input type="text" id="workout-name" class="input" placeholder="Örn: İtme Günü" required>
                </div>
            </div>

            <div style="margin-bottom: 16px;">
                <div style="display: flex; gap: 8px; align-items: flex-end;">
                    <div style="flex: 1;">
                        <label class="form-label">Egzersiz Ekle</label>
                        <select id="exercise-select" class="input" style="background: rgba(0,0,0,0.5);">
                            <!-- Populated from DB -->
                        </select>
                    </div>
                    <button type="button" id="add-exercise-btn" class="btn btn-secondary">Ekle</button>
                </div>
            </div>

            <div id="workout-exercises" style="max-height: 350px; overflow-y: auto; display: flex; flex-direction: column; gap: 16px; margin-bottom: 24px;">
                <!-- Added exercises and sets will appear here -->
            </div>

            <div class="dialog-actions" style="display: flex; justify-content: flex-end; gap: 12px;">
                <button type="button" class="btn btn-secondary" id="cancel-workout-dialog">İptal</button>
                <button type="button" class="btn btn-primary" id="save-workout-btn">İdmanı Kaydet</button>
            </div>
        </dialog>
    `;

    lucide.createIcons();

    // Data State
    let currentWorkout = {
        id: '',
        date: '',
        name: '',
        exercises: [] // { exerciseId, name, category, sets: [{ kg, reps, rpe }] }
    };
    let allExercises = [];

    // DOM Elements
    const startBtn = document.getElementById('start-workout-btn');
    const dialog = document.getElementById('workout-dialog');
    const closeBtn = document.getElementById('close-workout-dialog');
    const cancelBtn = document.getElementById('cancel-workout-dialog');
    const saveBtn = document.getElementById('save-workout-btn');
    const addExerciseBtn = document.getElementById('add-exercise-btn');
    const exerciseSelect = document.getElementById('exercise-select');
    const exercisesContainer = document.getElementById('workout-exercises');
    const dateInput = document.getElementById('workout-date');
    const nameInput = document.getElementById('workout-name');
    const historyList = document.getElementById('workout-history-list');

    dateInput.value = new Date().toISOString().split('T')[0];

    // Load Initial Data
    await loadExercises();
    await loadHistory();

    // Event Listeners
    startBtn.addEventListener('click', () => {
        currentWorkout = {
            id: 'workout_' + Date.now(),
            date: dateInput.value,
            name: '',
            exercises: []
        };
        nameInput.value = '';
        renderExercisesList();
        dialog.showModal();
    });

    const closeDialog = () => dialog.close();
    closeBtn.addEventListener('click', closeDialog);
    cancelBtn.addEventListener('click', closeDialog);

    addExerciseBtn.addEventListener('click', () => {
        const exId = exerciseSelect.value;
        const exName = exerciseSelect.options[exerciseSelect.selectedIndex].text;
        if (!exId) return;

        currentWorkout.exercises.push({
            exerciseId: exId,
            name: exName,
            sets: [{ kg: 0, reps: 0, rpe: 8 }]
        });
        renderExercisesList();
    });

    saveBtn.addEventListener('click', async () => {
        if (!nameInput.value.trim()) {
            alert('Lütfen idman adı girin.');
            return;
        }

        // Sync inputs back to state
        document.querySelectorAll('.set-row').forEach(row => {
            const exIndex = row.dataset.exIndex;
            const setIndex = row.dataset.setIndex;
            const kg = parseFloat(row.querySelector('.kg-input').value) || 0;
            const reps = parseInt(row.querySelector('.reps-input').value) || 0;
            const rpe = parseFloat(row.querySelector('.rpe-input').value) || 0;

            if (currentWorkout.exercises[exIndex]) {
                currentWorkout.exercises[exIndex].sets[setIndex] = { kg, reps, rpe };
            }
        });

        // Check for conflicts
        const allWorkouts = await DB.getAll('workouts');
        const recoveryStatus = calculateRecovery(allWorkouts);
        const conflicts = checkWorkoutConflict(currentWorkout.exercises, recoveryStatus);

        if (conflicts.length > 0) {
            if (!confirm("UYARI:\\n" + conflicts.join('\\n') + "\\n\\nYine de kaydetmek istiyor musunuz?")) {
                return;
            }
        }

        currentWorkout.name = nameInput.value;
        currentWorkout.date = dateInput.value;

        await DB.put('workouts', currentWorkout);
        dialog.close();
        await loadHistory();
    });

    async function loadExercises() {
        allExercises = await DB.getAll('exercises');
        if (allExercises.length === 0) {
            // Seed a few basic exercises if empty
            const seed = [
                { id: 'ex_1', name: 'Bench Press', category: 'Chest' },
                { id: 'ex_2', name: 'Squat', category: 'Legs' },
                { id: 'ex_3', name: 'Deadlift', category: 'Back' },
                { id: 'ex_4', name: 'Overhead Press', category: 'Shoulders' }
            ];
            for (let ex of seed) {
                await DB.put('exercises', ex);
            }
            allExercises = seed;
        }

        exerciseSelect.innerHTML = allExercises.map(ex => `<option value="${ex.id}">${ex.name} (${ex.category})</option>`).join('');
    }

    function renderExercisesList() {
        exercisesContainer.innerHTML = currentWorkout.exercises.map((ex, exIndex) => `
            <div class="exercise-card" style="background: rgba(255,255,255,0.05); padding: 16px; border-radius: 8px; border-left: 4px solid #3b82f6;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
                    <h4 style="margin: 0;">${ex.name}</h4>
                    <button class="btn-icon remove-ex-btn" data-ex-index="${exIndex}" style="color: #ef4444; background: transparent; border: none; cursor: pointer;">
                        <i data-lucide="trash-2"></i>
                    </button>
                </div>
                
                <div class="sets-container" style="display: flex; flex-direction: column; gap: 8px;">
                    <div style="display: grid; grid-template-columns: 1fr 2fr 2fr 2fr 1fr; gap: 8px; opacity: 0.7; font-size: 0.8rem; padding-bottom: 4px; border-bottom: 1px solid rgba(255,255,255,0.1);">
                        <span>Set</span>
                        <span>Kg</span>
                        <span>Tekrar</span>
                        <span>RPE</span>
                        <span></span>
                    </div>
                    ${ex.sets.map((set, setIndex) => `
                        <div class="set-row" data-ex-index="${exIndex}" data-set-index="${setIndex}" style="display: grid; grid-template-columns: 1fr 2fr 2fr 2fr 1fr; gap: 8px; align-items: center;">
                            <span style="font-weight: 500;">${setIndex + 1}</span>
                            <input type="number" class="input kg-input" value="${set.kg}" style="padding: 4px 8px; height: 32px;" min="0" step="0.5">
                            <input type="number" class="input reps-input" value="${set.reps}" style="padding: 4px 8px; height: 32px;" min="0">
                            <input type="number" class="input rpe-input" value="${set.rpe}" style="padding: 4px 8px; height: 32px;" min="5" max="10" step="0.5">
                            <button class="btn-icon remove-set-btn" data-ex-index="${exIndex}" data-set-index="${setIndex}" style="color: rgba(255,255,255,0.5); background: transparent; border: none; cursor: pointer; display: flex; align-items: center; justify-content: center;">
                                <i data-lucide="x" style="width: 16px; height: 16px;"></i>
                            </button>
                        </div>
                    `).join('')}
                </div>
                
                <button class="btn btn-secondary add-set-btn" data-ex-index="${exIndex}" style="margin-top: 12px; font-size: 0.85rem; padding: 6px 12px;">+ Set Ekle</button>
            </div>
        `).join('');

        lucide.createIcons();

        // Attach listeners for dynamic buttons inside list
        document.querySelectorAll('.remove-ex-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const idx = parseInt(e.currentTarget.dataset.exIndex);
                currentWorkout.exercises.splice(idx, 1);
                renderExercisesList();
            });
        });

        document.querySelectorAll('.add-set-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const idx = parseInt(e.currentTarget.dataset.exIndex);
                // Copy last set values or default
                const lastSet = currentWorkout.exercises[idx].sets[currentWorkout.exercises[idx].sets.length - 1] || { kg: 0, reps: 0, rpe: 8 };
                currentWorkout.exercises[idx].sets.push({ kg: lastSet.kg, reps: lastSet.reps, rpe: lastSet.rpe });
                
                // Preserve current inputs before re-rendering
                document.querySelectorAll('.set-row').forEach(row => {
                    const eI = row.dataset.exIndex;
                    const sI = row.dataset.setIndex;
                    currentWorkout.exercises[eI].sets[sI].kg = parseFloat(row.querySelector('.kg-input').value) || 0;
                    currentWorkout.exercises[eI].sets[sI].reps = parseInt(row.querySelector('.reps-input').value) || 0;
                    currentWorkout.exercises[eI].sets[sI].rpe = parseFloat(row.querySelector('.rpe-input').value) || 0;
                });
                
                renderExercisesList();
            });
        });

        document.querySelectorAll('.remove-set-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const eI = parseInt(e.currentTarget.dataset.exIndex);
                const sI = parseInt(e.currentTarget.dataset.setIndex);
                currentWorkout.exercises[eI].sets.splice(sI, 1);
                
                document.querySelectorAll('.set-row').forEach(row => {
                    const cEI = row.dataset.exIndex;
                    const cSI = row.dataset.setIndex;
                    if(currentWorkout.exercises[cEI] && currentWorkout.exercises[cEI].sets[cSI]) {
                        currentWorkout.exercises[cEI].sets[cSI].kg = parseFloat(row.querySelector('.kg-input').value) || 0;
                        currentWorkout.exercises[cEI].sets[cSI].reps = parseInt(row.querySelector('.reps-input').value) || 0;
                        currentWorkout.exercises[cEI].sets[cSI].rpe = parseFloat(row.querySelector('.rpe-input').value) || 0;
                    }
                });

                renderExercisesList();
            });
        });
    }

    async function loadHistory() {
        const workouts = await DB.getAll('workouts');
        workouts.sort((a, b) => new Date(b.date) - new Date(a.date));

        if (workouts.length === 0) {
            historyList.innerHTML = '<p style="padding: 16px; opacity: 0.5;">Henüz kaydedilmiş idman yok.</p>';
            return;
        }

        historyList.innerHTML = workouts.map(w => {
            let totalVolume = 0;
            w.exercises.forEach(ex => {
                ex.sets.forEach(set => {
                    totalVolume += (set.kg * set.reps);
                });
            });

            return `
                <div style="background: rgba(255,255,255,0.05); padding: 16px; border-radius: 8px; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h4 style="margin: 0; color: white;">${w.name}</h4>
                        <div style="display: flex; gap: 12px; margin-top: 4px; font-size: 0.85rem; opacity: 0.7;">
                            <span style="display: flex; align-items: center; gap: 4px;"><i data-lucide="calendar" style="width: 14px; height: 14px;"></i> ${new Date(w.date).toLocaleDateString('tr-TR')}</span>
                            <span style="display: flex; align-items: center; gap: 4px;"><i data-lucide="dumbbell" style="width: 14px; height: 14px;"></i> ${totalVolume} kg tonaj</span>
                        </div>
                    </div>
                    <button class="btn-icon delete-workout-btn" data-id="${w.id}" style="color: #ef4444; background: transparent; border: none; cursor: pointer;">
                        <i data-lucide="trash-2"></i>
                    </button>
                </div>
            `;
        }).join('');

        lucide.createIcons();

        document.querySelectorAll('.delete-workout-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                if(confirm('Bu idmanı silmek istediğinize emin misiniz?')) {
                    const id = e.currentTarget.dataset.id;
                    await DB.delete('workouts', id);
                    await loadHistory();
                }
            });
        });

        // Also we should update the volume chart here
        renderVolumeChart('volume-chart', workouts);
        render1RmRadarChart('radar-chart', workouts);
        renderRecoveryMap('recovery-map-container', workouts);
    }
}
