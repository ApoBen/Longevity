const CACHE_NAME = 'longevity-cache-v1';
const urlsToCache = [
    './',
    'index.html',
    'manifest.json',
    'css/variables.css',
    'css/base.css',
    'css/glassmorphism.css',
    'css/components.css',
    'css/navigation.css',
    'css/charts.css',
    'css/pages/dashboard.css',
    'css/pages/timeline.css',
    'css/pages/medications.css',
    'css/pages/labs.css',
    'css/pages/workouts.css',
    'css/pages/trends.css',
    'js/app.js',
    'js/db.js',
    'js/router.js',
    'js/data-importer.js',
    'js/pdf-parser.js',
    'js/pages/dashboard.js',
    'js/pages/timeline.js',
    'js/pages/medications.js',
    'js/pages/labs.js',
    'js/pages/workouts.js',
    'js/pages/trends.js',
    'js/charts/heart-rate-range.js',
    'js/charts/steps-hourly.js',
    'js/charts/spo2-chart.js',
    'js/charts/sleep-stages.js',
    'js/charts/pharma-curve.js',
    'js/charts/volume-chart.js',
    'js/charts/one-rm-radar.js',
    'js/charts/heatmap.js',
    'js/charts/recovery-map.js',
    'js/models/recovery.js'
];

self.addEventListener('install', event => {
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(cache => {
                console.log('Opened cache');
                return cache.addAll(urlsToCache);
            })
    );
});

self.addEventListener('fetch', event => {
    event.respondWith(
        caches.match(event.request)
            .then(response => {
                if (response) {
                    return response;
                }
                return fetch(event.request);
            })
    );
});

self.addEventListener('activate', event => {
    const cacheWhitelist = [CACHE_NAME];
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.map(cacheName => {
                    if (cacheWhitelist.indexOf(cacheName) === -1) {
                        return caches.delete(cacheName);
                    }
                })
            );
        })
    );
});
