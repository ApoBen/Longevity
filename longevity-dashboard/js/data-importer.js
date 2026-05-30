// Samsung Health JSON Data Importer - Longevity Platform
import { DB } from './db.js';

export const DataImporter = {
    /**
     * Parse and import a Samsung Health JSON export file into IndexedDB
     * @param {File} file 
     * @returns {Promise<boolean>}
     */
    importFile(file) {
        return new Promise((resolve, reject) => {
            if (!file) {
                reject(new Error('Dosya seçilmedi.'));
                return;
            }

            if (file.type !== 'application/json' && !file.name.endsWith('.json')) {
                reject(new Error('Lütfen geçerli bir JSON dosyası seçin.'));
                return;
            }

            const reader = new FileReader();

            reader.onerror = () => {
                reject(new Error('Dosya okunurken bir hata oluştu.'));
            };

            reader.onload = async (event) => {
                try {
                    const data = JSON.parse(event.target.result);
                    
                    // Validate schema
                    const isValid = this.validateSchema(data);
                    if (!isValid) {
                        reject(new Error('Geçersiz JSON şeması. Longevity v2.0 formatı bekleniyor.'));
                        return;
                    }

                    let importCount = 0;
                    
                    // Import days sequentially
                    for (const day of data.days) {
                        // Ensure date is present
                        if (!day.date) continue;
                        
                        // Upsert into IndexedDB health_data store
                        await DB.put('health_data', day);
                        importCount++;
                    }

                    console.log(`Successfully imported ${importCount} days of health records.`);
                    resolve({
                        success: true,
                        count: importCount,
                        exportDate: data.exportDate || null
                    });
                } catch (e) {
                    console.error('Failed to parse JSON:', e);
                    reject(new Error('JSON çözümlenemedi. Bozuk dosya olabilir.'));
                }
            };

            reader.readAsText(file);
        });
    },

    /**
     * Validates whether the imported JSON conforms to the required schema structure
     * @param {object} data 
     * @returns {boolean}
     */
    validateSchema(data) {
        if (!data || typeof data !== 'object') return false;
        
        // Check for version and days list
        if (!data.exportVersion || !Array.isArray(data.days)) {
            return false;
        }

        // Extremely basic validation on the first item if exists
        if (data.days.length > 0) {
            const first = data.days[0];
            if (!first.date) return false;
        }

        return true;
    }
};
