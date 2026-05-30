// Hash-based Client-side Router for Longevity SPA

const routes = {
    '/': { page: 'dashboard', modulePath: './pages/dashboard.js' },
    '/timeline': { page: 'timeline', modulePath: './pages/timeline.js' },
    '/medications': { page: 'medications', modulePath: './pages/medications.js' },
    '/labs': { page: 'labs', modulePath: './pages/labs.js' },
    '/workouts': { page: 'workouts', modulePath: './pages/workouts.js' },
    '/trends': { page: 'trends', modulePath: './pages/trends.js' }
};

const mainContent = document.getElementById('main-content');
const navItems = document.querySelectorAll('.bottom-nav .nav-item');

export const Router = {
    init() {
        window.addEventListener('hashchange', () => this.handleRoute());
        window.addEventListener('DOMContentLoaded', () => this.handleRoute());
    },

    async handleRoute() {
        let hash = window.location.hash || '#/';
        let path = hash.replace(/^#/, '');

        // Normalize path
        if (!path.startsWith('/')) {
            path = '/' + path;
        }

        const route = routes[path] || routes['/']; // Fallback to home

        // Highlight active bottom nav item
        this.updateNav(route.page);

        // Apply smooth transition (fade-out)
        mainContent.classList.add('fade-out');

        // Small timeout for fade-out transition (200ms)
        setTimeout(async () => {
            try {
                // Dynamically import the page module
                const module = await import(route.modulePath);
                
                // Clear and render new content
                mainContent.innerHTML = '';
                mainContent.className = 'main-content fade-in'; // remove fade-out, add fade-in
                
                // Call module's render function
                if (typeof module.render === 'function') {
                    await module.render(mainContent);
                } else {
                    mainContent.innerHTML = `<div class="error-container"><p>Modülde render fonksiyonu bulunamadı.</p></div>`;
                }

                // Re-trigger Lucide icons to render new dynamically added icons
                if (window.lucide) {
                    window.lucide.createIcons();
                }
            } catch (error) {
                console.error(`Page failed to load: ${route.modulePath}`, error);
                mainContent.innerHTML = `
                    <div class="error-container glass-card">
                        <i data-lucide="alert-triangle" class="error-icon"></i>
                        <h2>Sayfa Yüklenemedi</h2>
                        <p>İstenen modül yüklenirken bir hata oluştu.</p>
                        <p class="error-detail">${error.message}</p>
                        <button class="btn btn-primary" onclick="window.location.reload()">Sayfayı Yenile</button>
                    </div>
                `;
                if (window.lucide) {
                    window.lucide.createIcons();
                }
            }
        }, 150);
    },

    updateNav(activePage) {
        navItems.forEach(item => {
            item.classList.remove('active');
            
            // Map tab ids to routes
            const id = item.id;
            if (activePage === 'dashboard' && id === 'nav-home') item.classList.add('active');
            if (activePage === 'timeline' && id === 'nav-timeline') item.classList.add('active');
            if (activePage === 'medications' && id === 'nav-meds') item.classList.add('active');
            if (activePage === 'labs' && id === 'nav-labs') item.classList.add('active');
            if (activePage === 'workouts' && id === 'nav-workouts') item.classList.add('active');
            if (activePage === 'trends' && id === 'nav-trends') item.classList.add('active');
        });
    }
};
