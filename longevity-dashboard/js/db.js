// IndexedDB Database Manager for Longevity Platform

const DB_NAME = 'longevity_db';
const DB_VERSION = 1;
let dbInstance = null;

export const DB = {
    /**
     * Initializes the IndexedDB database.
     * @returns {Promise<IDBDatabase>}
     */
    init() {
        return new Promise((resolve, reject) => {
            if (dbInstance) {
                resolve(dbInstance);
                return;
            }

            const request = indexedDB.open(DB_NAME, DB_VERSION);

            request.onerror = (event) => {
                console.error('Database failed to open:', event.target.error);
                reject(event.target.error);
            };

            request.onsuccess = (event) => {
                dbInstance = event.target.result;
                console.log('Database opened successfully');
                resolve(dbInstance);
            };

            request.onupgradeneeded = (event) => {
                const db = event.target.result;
                console.log('Database upgrade needed, creating stores...');

                // 1. health_data: keyPath = date (YYYY-MM-DD)
                if (!db.objectStoreNames.contains('health_data')) {
                    const healthStore = db.createObjectStore('health_data', { keyPath: 'date' });
                    healthStore.createIndex('date', 'date', { unique: true });
                }

                // 2. medications: keyPath = id
                if (!db.objectStoreNames.contains('medications')) {
                    const medsStore = db.createObjectStore('medications', { keyPath: 'id' });
                    medsStore.createIndex('active', 'active', { unique: false });
                    medsStore.createIndex('name', 'name', { unique: false });
                }

                // 3. medication_logs: keyPath = id
                if (!db.objectStoreNames.contains('medication_logs')) {
                    const logsStore = db.createObjectStore('medication_logs', { keyPath: 'id' });
                    logsStore.createIndex('medicationId', 'medicationId', { unique: false });
                    logsStore.createIndex('timestamp', 'timestamp', { unique: false });
                    // Combined index for querying logs of a medication in chronological order
                    logsStore.createIndex('medication_time', ['medicationId', 'timestamp'], { unique: false });
                }

                // 4. lab_reports: keyPath = id
                if (!db.objectStoreNames.contains('lab_reports')) {
                    const labsStore = db.createObjectStore('lab_reports', { keyPath: 'id' });
                    labsStore.createIndex('date', 'date', { unique: false });
                }

                // 5. workouts: keyPath = id
                if (!db.objectStoreNames.contains('workouts')) {
                    const workoutsStore = db.createObjectStore('workouts', { keyPath: 'id' });
                    workoutsStore.createIndex('date', 'date', { unique: false });
                }

                // 6. exercises: keyPath = id
                if (!db.objectStoreNames.contains('exercises')) {
                    const exercisesStore = db.createObjectStore('exercises', { keyPath: 'id' });
                    exercisesStore.createIndex('category', 'category', { unique: false });
                    exercisesStore.createIndex('name', 'name', { unique: false });
                }
            };
        });
    },

    /**
     * Generic write (add or update)
     * @param {string} storeName 
     * @param {object} item 
     * @returns {Promise<any>}
     */
    put(storeName, item) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readwrite');
                const store = transaction.objectStore(storeName);
                const request = store.put(item);

                request.onsuccess = () => resolve(request.result);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Generic read by key
     * @param {string} storeName 
     * @param {any} key 
     * @returns {Promise<any>}
     */
    get(storeName, key) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readonly');
                const store = transaction.objectStore(storeName);
                const request = store.get(key);

                request.onsuccess = () => resolve(request.result || null);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Generic delete by key
     * @param {string} storeName 
     * @param {any} key 
     * @returns {Promise<any>}
     */
    delete(storeName, key) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readwrite');
                const store = transaction.objectStore(storeName);
                const request = store.delete(key);

                request.onsuccess = () => resolve(true);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Get all items in a store
     * @param {string} storeName 
     * @returns {Promise<Array<any>>}
     */
    getAll(storeName) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readonly');
                const store = transaction.objectStore(storeName);
                const request = store.getAll();

                request.onsuccess = () => resolve(request.result || []);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Query by index
     * @param {string} storeName 
     * @param {string} indexName 
     * @param {any} queryValue 
     * @returns {Promise<Array<any>>}
     */
    getByIndex(storeName, indexName, queryValue) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readonly');
                const store = transaction.objectStore(storeName);
                const index = store.index(indexName);
                const request = index.getAll(queryValue);

                request.onsuccess = () => resolve(request.result || []);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Fetch records within a date range
     * Works for stores indexed by 'date' (health_data, workouts, lab_reports)
     * @param {string} storeName 
     * @param {string} startDate YYYY-MM-DD
     * @param {string} endDate YYYY-MM-DD
     * @returns {Promise<Array<any>>}
     */
    getByDateRange(storeName, startDate, endDate) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction([storeName], 'readonly');
                const store = transaction.objectStore(storeName);
                const index = store.index('date');
                const range = IDBKeyRange.bound(startDate, endDate);
                const request = index.getAll(range);

                request.onsuccess = () => resolve(request.result || []);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    },

    /**
     * Custom query for medication logs in a specific range
     * @param {string} medicationId 
     * @param {string} startTimestamp ISO String
     * @param {string} endTimestamp ISO String
     * @returns {Promise<Array<any>>}
     */
    getMedicationLogsByRange(medicationId, startTimestamp, endTimestamp) {
        return this.init().then((db) => {
            return new Promise((resolve, reject) => {
                const transaction = db.transaction(['medication_logs'], 'readonly');
                const store = transaction.objectStore('medication_logs');
                const index = store.index('medication_time');
                const range = IDBKeyRange.bound([medicationId, startTimestamp], [medicationId, endTimestamp]);
                const request = index.getAll(range);

                request.onsuccess = () => resolve(request.result || []);
                request.onerror = (e) => reject(e.target.error);
            });
        });
    }
};
