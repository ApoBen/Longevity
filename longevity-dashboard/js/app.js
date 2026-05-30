// Longevity Platform main application entry point
import { DB } from './db.js';
import { Router } from './router.js';
import { INITIAL_EXERCISES } from './models/exercise-db.js';

// Setup Global Application State
window.LongevityState = {
    // Current selected date in the dashboard (YYYY-MM-DD)
    selectedDate: getLocalDateString(new Date()),
    
    // Listeners that are called when date changes
    dateChangeListeners: [],
    
    /**
     * Set the selected date and notify all active listeners
     * @param {string} dateStr YYYY-MM-DD
     */
    setDate(dateStr) {
        if (this.selectedDate !== dateStr) {
            this.selectedDate = dateStr;
            this.dateChangeListeners.forEach(listener => {
                try {
                    listener(dateStr);
                } catch (e) {
                    console.error("Error invoking date change listener:", e);
                }
            });
        }
    },
    
    /**
     * Register a callback for date changes
     * @param {Function} callback (dateStr) => void
     */
    onDateChange(callback) {
        this.dateChangeListeners.push(callback);
    },
    
    /**
     * Unregister a callback for date changes
     * @param {Function} callback 
     */
    offDateChange(callback) {
        this.dateChangeListeners = this.dateChangeListeners.filter(l => l !== callback);
    }
};

// Global Toast Notification Helper
window.showToast = function(message, type = 'info') {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${type} glass-card`;
    
    // Choose icon depending on type
    let iconName = 'info';
    if (type === 'success') iconName = 'check-circle';
    if (type === 'warning') iconName = 'alert-triangle';
    if (type === 'danger') iconName = 'alert-circle';

    toast.innerHTML = `
        <i data-lucide="${iconName}" class="toast-icon"></i>
        <span class="toast-message">${message}</span>
        <button class="toast-close">&times;</button>
    `;

    container.appendChild(toast);
    
    if (window.lucide) {
        window.lucide.createIcons();
    }

    // Slide in
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);

    // Auto-remove after 3 seconds
    const autoRemove = setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    }, 3000);

    // Close button click
    toast.querySelector('.toast-close').addEventListener('click', () => {
        clearTimeout(autoRemove);
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    });
};

// Helper function to get YYYY-MM-DD in local time
function getLocalDateString(date) {
    const offset = date.getTimezoneOffset();
    const localDate = new Date(date.getTime() - (offset * 60 * 1000));
    return localDate.toISOString().split('T')[0];
}

// Exercise Database Seeding
async function seedExercisesIfNeeded() {
    try {
        const existing = await DB.getAll('exercises');
        if (existing.length === 0) {
            console.log('Seeding initial exercises into IndexedDB...');
            for (const exercise of INITIAL_EXERCISES) {
                await DB.put('exercises', exercise);
            }
            console.log(`Successfully seeded ${INITIAL_EXERCISES.length} exercises.`);
        } else {
            console.log(`Exercise database already seeded (${existing.length} items).`);
        }
    } catch (e) {
        console.error('Failed to seed exercises:', e);
    }
}

// App Initialization
async function initializeApp() {
    console.log('Initializing Longevity Platform App...');
    
    // Register Service Worker
    if ('serviceWorker' in navigator) {
        window.addEventListener('load', () => {
            navigator.serviceWorker.register('/sw.js').then(registration => {
                console.log('SW registered: ', registration);
            }).catch(registrationError => {
                console.log('SW registration failed: ', registrationError);
            });
        });
    }

    try {
        // Initialize DB
        await DB.init();
        
        // Seed database if necessary
        await seedExercisesIfNeeded();
        
        // Initialize SPA Router
        Router.init();
        
        // Initial SVG Lucide icons rendering
        if (window.lucide) {
            window.lucide.createIcons();
        }
        
        console.log('Longevity Platform fully initialized.');
    } catch (error) {
        console.error('App initialization failed:', error);
        window.showToast('Uygulama yüklenirken bir hata oluştu.', 'danger');
    }
}

// Start
initializeApp();
