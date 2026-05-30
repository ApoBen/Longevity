package com.shealt.healthreport.data.model

data class ExportData(
    val exportVersion: String = "2.0",
    val exportDate: String,
    val days: List<DailyHealthReport>
)
