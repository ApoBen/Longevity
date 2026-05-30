package com.shealt.healthreport.data.model

data class HourlyDataPoint(
    val hour: Int, // 0 to 23
    val value: Float,
    val count: Int = 1 // Useful for averages
)
