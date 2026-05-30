import { calculateRecovery } from '../models/recovery.js';

export function renderRecoveryMap(containerId, workouts) {
    const container = document.getElementById(containerId);
    if (!container) return;

    const recoveryStatus = calculateRecovery(workouts);

    // Simple textual representation or basic SVG/bars since creating a full complex SVG body map in JS string is long.
    // We'll do a CSS-based body map using simple blocks or flex rows.

    const html = Object.keys(recoveryStatus).map(group => {
        const value = Math.round(recoveryStatus[group]);
        let color = '#4ade80'; // Green
        let label = 'Hazır';

        if (value < 40) {
            color = '#ef4444'; // Red
            label = 'Yorgun';
        } else if (value < 75) {
            color = '#facc15'; // Yellow
            label = 'Toparlanıyor';
        }

        return `
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
                <span style="width: 80px;">${group}</span>
                <div style="flex: 1; background: rgba(255,255,255,0.1); height: 8px; border-radius: 4px; margin: 0 12px; overflow: hidden;">
                    <div style="width: ${value}%; background: ${color}; height: 100%; transition: width 0.3s ease;"></div>
                </div>
                <span style="font-size: 0.8rem; color: ${color}; width: 80px; text-align: right;">${value}% (${label})</span>
            </div>
        `;
    }).join('');

    container.innerHTML = `
        <div style="padding: 8px;">
            ${html}
        </div>
    `;
}
