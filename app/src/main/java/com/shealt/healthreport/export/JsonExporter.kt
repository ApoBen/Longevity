package com.shealt.healthreport.export

import android.content.Context
import android.os.Environment
import com.google.gson.GsonBuilder
import com.google.gson.JsonSerializer
import com.shealt.healthreport.data.model.DailyHealthReport
import com.shealt.healthreport.data.model.ExportData
import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class JsonExporter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun exportReport(report: DailyHealthReport): File? {
        return try {
            val gson = createGson()
            
            val exportData = ExportData(
                exportDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                days = listOf(report)
            )
            
            val jsonString = gson.toJson(exportData)

            val docsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val longevityDir = File(docsDir, "Longevity")
            if (!longevityDir.exists()) {
                longevityDir.mkdirs()
            }

            val fileName = "Longevity_Data_${report.date}.json"
            val file = File(longevityDir, fileName)

            FileWriter(file).use {
                it.write(jsonString)
            }
            
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun exportMultipleReports(reports: List<DailyHealthReport>, startDate: LocalDate, endDate: LocalDate): File? {
        return try {
            val gson = createGson()
            
            val exportData = ExportData(
                exportDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                days = reports
            )
            
            val jsonString = gson.toJson(exportData)

            val docsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val longevityDir = File(docsDir, "Longevity")
            if (!longevityDir.exists()) {
                longevityDir.mkdirs()
            }

            val fileName = "Longevity_Data_${startDate}_to_${endDate}.json"
            val file = File(longevityDir, fileName)

            FileWriter(file).use {
                it.write(jsonString)
            }
            
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun createGson() = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime::class.java, JsonSerializer<LocalDateTime> { src, _, _ ->
            com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        })
        .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
            com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
        })
        .create()
}
