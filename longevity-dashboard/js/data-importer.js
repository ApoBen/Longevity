// Samsung Health JSON/PDF Data Importer - Longevity Platform
import { DB } from './db.js';

export const DataImporter = {
    /**
     * Parse and import a Samsung Health export file (PDF or JSON) into IndexedDB
     * @param {File} file 
     * @returns {Promise<object>}
     */
    importFile(file) {
        return new Promise(async (resolve, reject) => {
            if (!file) {
                reject(new Error('Dosya seçilmedi.'));
                return;
            }

            if (file.type !== 'application/json' && !file.name.endsWith('.json') && 
                file.type !== 'application/pdf' && !file.name.endsWith('.pdf')) {
                reject(new Error('Lütfen geçerli bir PDF veya JSON dosyası seçin.'));
                return;
            }

            try {
                let jsonString = "";

                if (file.type === 'application/pdf' || file.name.endsWith('.pdf')) {
                    jsonString = await this.extractJsonFromPdf(file);
                } else {
                    jsonString = await file.text();
                }

                const data = JSON.parse(jsonString);
                
                // Validate schema
                const isValid = this.validateSchema(data);
                if (!isValid) {
                    reject(new Error('Geçersiz veri şeması. Longevity v2.0 formatı bekleniyor.'));
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
                console.error('Failed to parse data:', e);
                reject(new Error(e.message || 'Veri çözümlenemedi. Bozuk dosya veya desteklenmeyen format olabilir.'));
            }
        });
    },

    /**
     * Extracts embedded JSON text from a PDF file using pdf.js
     * @param {File} file 
     * @returns {Promise<string>}
     */
    async extractJsonFromPdf(file) {
        if (!window.pdfjsLib) {
            throw new Error('PDF okuyucu kütüphanesi yüklenemedi.');
        }

        const arrayBuffer = await file.arrayBuffer();
        const pdf = await window.pdfjsLib.getDocument({ data: arrayBuffer }).promise;
        
        let fullText = "";
        
        // Read text from all pages
        for (let i = 1; i <= pdf.numPages; i++) {
            const page = await pdf.getPage(i);
            const textContent = await page.getTextContent();
            
            // In pdf.js, text items might be disjointed, so we just concatenate them
            // We don't add spaces because our Android app writes the base64/json 
            // as contiguous strings or short lines without relying on spaces
            const pageText = textContent.items.map(item => item.str).join('');
            fullText += pageText;
        }

        // Search for our delimiters
        const startMarker = "---LONGEVITY-DATA-START---";
        const endMarker = "---LONGEVITY-DATA-END---";
        
        const startIndex = fullText.indexOf(startMarker);
        const endIndex = fullText.indexOf(endMarker);
        
        if (startIndex === -1 || endIndex === -1 || startIndex >= endIndex) {
            throw new Error('PDF dosyasında geçerli bir Longevity veri bloğu bulunamadı.');
        }
        
        const jsonBlock = fullText.substring(startIndex + startMarker.length, endIndex).trim();
        return jsonBlock;
    },

    /**
     * Validates whether the imported JSON conforms to the required schema structure
     * @param {object} data 
     * @returns {boolean}
     */
    validateSchema(data) {
        if (!data || typeof data !== 'object') return false;
        
        // Check for version and days list
        if (data.exportVersion !== '2.0' || !Array.isArray(data.days)) {
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
