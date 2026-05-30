package com.shealt.healthreport.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.graphics.RectF
import com.shealt.healthreport.data.model.DailyHealthReport
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfReportGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun generatePdf(report: DailyHealthReport): File? {
        val document = PdfDocument()
        
        // Page 1: Summary, Activity, Heart Rate, Sleep, Water
        val pageInfo1 = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page1 = document.startPage(pageInfo1)
        drawPage1(page1.canvas, report)
        document.finishPage(page1)

        // Page 2: Medical Stats, Body Composition, Nutrition, Workouts
        val pageInfo2 = PdfDocument.PageInfo.Builder(595, 842, 2).create()
        val page2 = document.startPage(pageInfo2)
        drawPage2(page2.canvas, report)
        document.finishPage(page2)

        return savePdf(document, report)
    }

    private fun drawPage1(canvas: Canvas, report: DailyHealthReport) {
        val paintTitle = Paint().apply {
            color = Color.rgb(0, 51, 102) // Navy Blue
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintSectionHeader = Paint().apply {
            color = Color.rgb(0, 102, 153) // Lighter Blue
            textSize = 16f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintBoldText = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintNormalText = Paint().apply {
            color = Color.DKGRAY
            textSize = 12f
            isAntiAlias = true
        }

        val paintLine = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
            isAntiAlias = true
        }

        // Header
        canvas.drawText("GÜNLÜK SAĞLIK RAPORU", 50f, 60f, paintTitle)
        
        val dateStr = report.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        canvas.drawText("Rapor Tarihi: $dateStr", 50f, 85f, paintNormalText)

        canvas.drawLine(50f, 115f, 545f, 115f, paintLine)

        var yPos = 145f
        val stepSpacing = 20f
        val sectionSpacing = 35f

        // 1. Aktivite ve Adımlar
        canvas.drawText("1. Fiziksel Aktivite & Adımlar", 50f, yPos, paintSectionHeader)
        yPos += 20f
        report.steps?.let { steps ->
            if (steps.total > 0) {
                val distKm = String.format("%.2f", steps.distanceMeters / 1000.0)
                canvas.drawText("• Toplam Adım: ${steps.total} / Hedef: ${steps.goal} | Yürünen Mesafe: $distKm km", 60f, yPos, paintNormalText)
                yPos += stepSpacing
                
                // Progress Bar for Steps
                val progressWidth = 450f
                val progressHeight = 15f
                val progress = (steps.total.toFloat() / steps.goal.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
                
                val bgPaint = Paint().apply { color = Color.LTGRAY; isAntiAlias = true }
                val fillPaint = Paint().apply { color = Color.rgb(0, 153, 76); isAntiAlias = true } // Green
                
                canvas.drawRoundRect(RectF(60f, yPos, 60f + progressWidth, yPos + progressHeight), 8f, 8f, bgPaint)
                canvas.drawRoundRect(RectF(60f, yPos, 60f + (progressWidth * progress), yPos + progressHeight), 8f, 8f, fillPaint)
                
                yPos += progressHeight + stepSpacing
            }
        }
        report.floors?.let { floors ->
            if (floors.climbed > 0) {
                canvas.drawText("• Çıkılan Kat: ${floors.climbed} kat / Hedef: ${floors.goal} kat", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        }
        if ((report.steps?.total ?: 0) == 0 && (report.floors?.climbed ?: 0) == 0) {
            canvas.drawText("Kayıtlı fiziksel aktivite verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 2. Kalp Sağlığı (Nabız)
        canvas.drawText("2. Kalp Sağlığı (Nabız)", 50f, yPos, paintSectionHeader)
        yPos += 20f
        report.heartRate?.let { hr ->
            if (hr.dailySummary.avg > 0) {
                canvas.drawText("• Ortalama Nabız: ${hr.dailySummary.avg} bpm | Aralık: ${hr.dailySummary.min} - ${hr.dailySummary.max} bpm", 60f, yPos, paintNormalText)
                yPos += stepSpacing
                hr.dailySummary.resting?.let { resting ->
                    canvas.drawText("• Dinlenme Nabzı: $resting bpm", 60f, yPos, paintNormalText)
                    yPos += stepSpacing
                }

                // Heart Rate Band Chart (Min-Max)
                val bandWidth = 450f
                val bandHeight = 12f
                val hrMin = 40f
                val hrMax = 200f
                val range = hrMax - hrMin
                
                val bgPaint = Paint().apply { color = Color.rgb(240, 240, 240); isAntiAlias = true }
                val valPaint = Paint().apply { color = Color.rgb(204, 0, 0); isAntiAlias = true } // Red
                
                canvas.drawRoundRect(RectF(60f, yPos, 60f + bandWidth, yPos + bandHeight), 6f, 6f, bgPaint)
                
                val startX = 60f + ((hr.dailySummary.min.toFloat() - hrMin) / range * bandWidth).coerceIn(0f, bandWidth)
                val endX = 60f + ((hr.dailySummary.max.toFloat() - hrMin) / range * bandWidth).coerceIn(0f, bandWidth)
                val avgX = 60f + ((hr.dailySummary.avg.toFloat() - hrMin) / range * bandWidth).coerceIn(0f, bandWidth)
                
                canvas.drawRoundRect(RectF(startX, yPos, endX, yPos + bandHeight), 6f, 6f, valPaint)
                
                // Draw Average Marker
                val avgPaint = Paint().apply { color = Color.BLACK; strokeWidth = 3f; isAntiAlias = true }
                canvas.drawLine(avgX, yPos - 4f, avgX, yPos + bandHeight + 4f, avgPaint)
                
                yPos += bandHeight + stepSpacing
            } else {
                canvas.drawText("Kayıtlı nabız verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } ?: run {
            canvas.drawText("Kayıtlı nabız verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 3. Enerji Skoru & Kalori Dengesi
        canvas.drawText("3. Enerji Skoru & Kalori Dengesi", 50f, yPos, paintSectionHeader)
        yPos += 20f
        report.energyScore?.let { energyScore ->
            canvas.drawText("• Günlük Enerji Skoru: $energyScore / 100", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }
        report.calories?.let { cal ->
            canvas.drawText("• Toplam Yakılan Kalori: ${String.format("%.1f", cal.total)} kcal", 60f, yPos, paintNormalText)
            yPos += stepSpacing
            canvas.drawText("• Aktif Kalori: ${String.format("%.1f", cal.active)} kcal | Dinlenme Kalorisi: ${String.format("%.1f", cal.rest)} kcal", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }
        if (report.energyScore == null && report.calories == null) {
            canvas.drawText("Kayıtlı veri bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 4. Detaylı Uyku Analizi
        canvas.drawText("4. Uyku Analizi", 50f, yPos, paintSectionHeader)
        yPos += 20f
        report.sleep?.let { sleep ->
            if (sleep.totalMinutes > 0) {
                val hrs = sleep.totalMinutes / 60
                val mins = sleep.totalMinutes % 60
                val scoreStr = sleep.score?.let { " (Uyku Skoru: $it/100)" } ?: ""
                
                canvas.drawText("• Toplam Uyku Süresi: $hrs saat $mins dakika$scoreStr", 60f, yPos, paintNormalText)
                yPos += stepSpacing
                
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                canvas.drawText("• Yatma Zamanı: ${sleep.startTime.format(formatter)} | Uyanma Zamanı: ${sleep.endTime.format(formatter)}", 60f, yPos, paintNormalText)
                yPos += stepSpacing

                // Sleep Stages Breakdown & Graph
                if (sleep.stages.rem > 0 || sleep.stages.light > 0 || sleep.stages.deep > 0 || sleep.stages.awake > 0) {
                    canvas.drawText("• Uyku Evreleri Kırılımı:", 60f, yPos, paintBoldText)
                    yPos += stepSpacing
                    
                    val totalTracked = (sleep.stages.rem + sleep.stages.light + sleep.stages.deep + sleep.stages.awake).toFloat()
                    if (totalTracked > 0) {
                        // Draw Segmented Bar Chart
                        val barWidth = 450f
                        val barHeight = 20f
                        var currentX = 60f
                        
                        val awakePaint = Paint().apply { color = Color.rgb(255, 102, 102) } // Redish
                        val remPaint = Paint().apply { color = Color.rgb(102, 178, 255) } // Light blue
                        val lightPaint = Paint().apply { color = Color.rgb(0, 102, 204) } // Med blue
                        val deepPaint = Paint().apply { color = Color.rgb(0, 0, 102) } // Dark blue
                        
                        val wAwake = (sleep.stages.awake / totalTracked) * barWidth
                        val wRem = (sleep.stages.rem / totalTracked) * barWidth
                        val wLight = (sleep.stages.light / totalTracked) * barWidth
                        val wDeep = (sleep.stages.deep / totalTracked) * barWidth
                        
                        // Uyanık
                        if (wAwake > 0) canvas.drawRect(currentX, yPos, currentX + wAwake, yPos + barHeight, awakePaint)
                        currentX += wAwake
                        // REM
                        if (wRem > 0) canvas.drawRect(currentX, yPos, currentX + wRem, yPos + barHeight, remPaint)
                        currentX += wRem
                        // Hafif
                        if (wLight > 0) canvas.drawRect(currentX, yPos, currentX + wLight, yPos + barHeight, lightPaint)
                        currentX += wLight
                        // Derin
                        if (wDeep > 0) canvas.drawRect(currentX, yPos, currentX + wDeep, yPos + barHeight, deepPaint)
                        
                        yPos += barHeight + 10f
                        
                        // Legend
                        canvas.drawText("Uyanık: ${sleep.stages.awake}dk", 60f, yPos, Paint(paintNormalText).apply { color = awakePaint.color })
                        canvas.drawText("REM: ${sleep.stages.rem}dk", 160f, yPos, Paint(paintNormalText).apply { color = remPaint.color })
                        canvas.drawText("Hafif: ${sleep.stages.light}dk", 250f, yPos, Paint(paintNormalText).apply { color = lightPaint.color })
                        canvas.drawText("Derin: ${sleep.stages.deep}dk", 340f, yPos, Paint(paintNormalText).apply { color = deepPaint.color })
                        
                        yPos += stepSpacing
                    }
                }
            } else {
                canvas.drawText("Kayıtlı uyku verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } ?: run {
            canvas.drawText("Kayıtlı uyku verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 5. Sıvı Tüketimi
        canvas.drawText("5. Sıvı Tüketimi (Su)", 50f, yPos, paintSectionHeader)
        yPos += 20f
        report.waterIntake?.let { water ->
            if (water.amountMl > 0) {
                canvas.drawText("• Alınan Su: ${String.format("%.0f", water.amountMl)} ml / Hedef: ${String.format("%.0f", water.goalMl)} ml", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            } else {
                canvas.drawText("Sıvı tüketim kaydı (0 ml) bulunamadı.", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } ?: run {
            canvas.drawText("Sıvı tüketim verisi bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }
    }

    private fun drawPage2(canvas: Canvas, report: DailyHealthReport) {
        val paintHeader = Paint().apply {
            color = Color.rgb(0, 51, 102)
            textSize = 20f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintSectionHeader = Paint().apply {
            color = Color.rgb(0, 102, 153)
            textSize = 15f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val paintNormalText = Paint().apply {
            color = Color.DKGRAY
            textSize = 11f
            isAntiAlias = true
        }

        val paintLine = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
            isAntiAlias = true
        }

        canvas.drawText("DETAYLI ÖLÇÜMLER & TIBBİ VERİLER", 50f, 60f, paintHeader)
        canvas.drawLine(50f, 75f, 545f, 75f, paintLine)

        var yPos = 105f
        val stepSpacing = 17f
        val sectionSpacing = 30f

        // 6. Vücut Analizi
        canvas.drawText("6. Vücut Kompozisyonu (Vücut Analizi)", 50f, yPos, paintSectionHeader)
        yPos += 18f
        report.bodyComposition?.let { bc ->
            canvas.drawText("• Ağırlık: ${bc.weightKg} kg", 60f, yPos, paintNormalText)
            yPos += stepSpacing
            bc.bodyFat?.let {
                canvas.drawText("• Vücut Yağ Oranı: % $it", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
            bc.muscleMass?.let {
                canvas.drawText("• İskelet Kas Kütlesi: $it kg", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
            bc.bmi?.let {
                canvas.drawText("• Vücut Kitle Endeksi (BMI): $it", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } ?: run {
            canvas.drawText("Kayıtlı veri bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 7. Beslenme Takibi
        canvas.drawText("7. Beslenme & Makro Değerler", 50f, yPos, paintSectionHeader)
        yPos += 18f
        report.nutrition?.let { nut ->
            canvas.drawText("• Tüketilen Toplam Enerji: ${String.format("%.1f", nut.calories)} kcal", 60f, yPos, paintNormalText)
            yPos += stepSpacing
            val macroStr = buildString {
                nut.carbs?.let { append("Karbonhidrat: ${String.format("%.1f", it)}g | ") }
                nut.protein?.let { append("Protein: ${String.format("%.1f", it)}g | ") }
                nut.fat?.let { append("Yağ: ${String.format("%.1f", it)}g") }
            }
            if (macroStr.isNotEmpty()) {
                canvas.drawText("• Makro Besin Kırılımı: $macroStr", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
            nut.fiber?.let {
                canvas.drawText("• Diyet Lifi: ${String.format("%.1f", it)}g", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } ?: run {
            canvas.drawText("Kayıtlı veri bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 8. Tıbbi Ölçümler
        canvas.drawText("8. Kardiyovasküler & Metabolik Ölçümler", 50f, yPos, paintSectionHeader)
        yPos += 18f
        
        var hasMedicalData = false

        if (report.bloodPressure.isNotEmpty()) {
            hasMedicalData = true
            canvas.drawText("• Tansiyon Ölçümleri (mmHg):", 60f, yPos, paintSectionHeader)
            yPos += 15f
            report.bloodPressure.take(3).forEach { bp ->
                val pulseStr = bp.pulse?.let { " (Nabız: $it)" } ?: ""
                canvas.drawText("  - ${bp.time} -> Sistolik: ${bp.systolic.toInt()} | Diastolik: ${bp.diastolic.toInt()} $pulseStr", 70f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        }

        report.bloodOxygen?.hourly?.let { boHourly ->
            if (boHourly.isNotEmpty()) {
                hasMedicalData = true
                canvas.drawText("• Kan Oksijen Doygunluğu (SpO2):", 60f, yPos, paintSectionHeader)
                yPos += 15f
                boHourly.take(3).forEach { bo ->
                    canvas.drawText("  - Saat ${bo.hour}:00 -> % ${bo.avg.toInt()}", 70f, yPos, paintNormalText)
                    yPos += stepSpacing
                }
            }
        }

        if (report.bloodGlucose.isNotEmpty()) {
            hasMedicalData = true
            canvas.drawText("• Kan Şekeri Ölçümleri (mg/dL):", 60f, yPos, paintSectionHeader)
            yPos += 15f
            report.bloodGlucose.take(3).forEach { bg ->
                val typeStr = bg.mealType?.let { " (${translateMealType(it)})" } ?: ""
                canvas.drawText("  - ${bg.time} -> ${bg.glucose.toInt()} mg/dL$typeStr", 70f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        }

        report.skinTemperature?.hourly?.let { stHourly ->
            if (stHourly.isNotEmpty()) {
                hasMedicalData = true
                val avgTemp = stHourly.map { it.avg }.average()
                canvas.drawText("• Ortalama Cilt Sıcaklığı: ${String.format("%.1f", avgTemp)} °C", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        }

        if (!hasMedicalData) {
            canvas.drawText("Grup içinde kayıtlı kardiyovasküler veya metabolik ölçüm bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }

        yPos += sectionSpacing

        // 9. Yapılan Egzersiz Seansları
        canvas.drawText("9. Yapılan Egzersiz Seansları", 50f, yPos, paintSectionHeader)
        yPos += 18f
        if (report.workouts.isNotEmpty()) {
            report.workouts.take(5).forEach { workout ->
                val duration = workout.durationMin
                val cal = String.format("%.1f", workout.calories)
                val distStr = workout.distanceM?.let { String.format(" | Mesafe: %.2f km", it / 1000.0) } ?: ""
                canvas.drawText("• ${workout.type}: $duration dk - $cal kcal$distStr", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
            if (report.workouts.size > 5) {
                canvas.drawText("...ve ${report.workouts.size - 5} egzersiz seansı daha yapıldı.", 60f, yPos, paintNormalText)
                yPos += stepSpacing
            }
        } else {
            canvas.drawText("Gün içinde kayıtlı egzersiz seansı bulunmamaktadır.", 60f, yPos, paintNormalText)
            yPos += stepSpacing
        }
    }

    private fun translateMealType(type: String): String {
        return when (type) {
            "FASTING" -> "Açlık"
            "AFTER_MEAL" -> "Tokluk"
            "BEFORE_BREAKFAST" -> "Kahvaltı Önce"
            "AFTER_BREAKFAST" -> "Kahvaltı Sonra"
            "BEFORE_LUNCH" -> "Öğle Önce"
            "AFTER_LUNCH" -> "Öğle Sonra"
            "BEFORE_DINNER" -> "Akşam Önce"
            "AFTER_DINNER" -> "Akşam Sonra"
            "BEFORE_SLEEP" -> "Gece Uykudan Önce"
            "GENERAL" -> "Genel"
            else -> type
        }
    }

    private fun savePdf(document: PdfDocument, report: DailyHealthReport): File? {
        val dateStr = report.date.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"))
        val fileName = "HealthReport_$dateStr.pdf"
        
        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "HealthReports")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        
        val file = File(directory, fileName)
        
        return try {
            FileOutputStream(file).use { out ->
                document.writeTo(out)
            }
            document.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            null
        }
    }
}
