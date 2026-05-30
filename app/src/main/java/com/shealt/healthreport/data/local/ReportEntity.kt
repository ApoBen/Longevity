package com.shealt.healthreport.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateString: String,              // Format: "YYYY-MM-DD"
    val filePath: String,                // Path to local PDF file
    val createdAtTimestamp: Long,        // System timestamp when PDF was created
    val stepCount: Int?,
    val sleepScore: Int?,
    val energyScore: Int?,
    val avgHeartRate: Int?,
    val workoutCount: Int?,
    val sleepDurationMinutes: Int?
)
