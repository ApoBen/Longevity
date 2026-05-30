package com.shealt.healthreport.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyHealthReport(
    val date: LocalDate,
    val heartRate: HeartRateData? = null,
    val bloodOxygen: BloodOxygenData? = null,
    val steps: StepData? = null,
    val skinTemperature: SkinTemperatureData? = null,
    val sleep: SleepData? = null,
    val calories: CalorieData? = null,
    val bloodPressure: List<BloodPressureData> = emptyList(),
    val bloodGlucose: List<BloodGlucoseData> = emptyList(),
    val bodyComposition: BodyCompositionData? = null,
    val nutrition: NutritionData? = null,
    val waterIntake: WaterIntakeData? = null,
    val floors: FloorData? = null,
    val energyScore: Int? = null,
    val workouts: List<WorkoutData> = emptyList()
)

data class HeartRateData(
    val dailySummary: HeartRateSummary,
    val hourly: List<HourlyHeartRate> = emptyList()
)

data class HeartRateSummary(
    val avg: Int,
    val min: Int,
    val max: Int,
    val resting: Int?
)

data class HourlyHeartRate(
    val hour: Int,
    val min: Int,
    val max: Int,
    val avg: Int,
    val count: Int
)

data class BloodOxygenData(
    val hourly: List<HourlyBloodOxygen> = emptyList()
)

data class HourlyBloodOxygen(
    val hour: Int,
    val avg: Double,
    val min: Double,
    val count: Int
)

data class StepData(
    val total: Int,
    val goal: Int,
    val distanceMeters: Double,
    val hourly: List<HourlySteps> = emptyList()
)

data class HourlySteps(
    val hour: Int,
    val steps: Int
)

data class SkinTemperatureData(
    val hourly: List<HourlySkinTemperature> = emptyList()
)

data class HourlySkinTemperature(
    val hour: Int,
    val avg: Double,
    val count: Int
)

data class SleepData(
    val totalMinutes: Int,
    val score: Int?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val stages: SleepStages
)

data class SleepStages(
    val rem: Int,
    val light: Int,
    val deep: Int,
    val awake: Int
)

data class CalorieData(
    val total: Double,
    val active: Double,
    val rest: Double
)

data class BloodPressureData(
    val systolic: Double,
    val diastolic: Double,
    val pulse: Int?,
    val time: String // "09:00" format expected by JSON
)

data class BloodGlucoseData(
    val glucose: Double,
    val mealType: String?,
    val time: String // "07:00"
)

data class BodyCompositionData(
    val weightKg: Double,
    val bodyFat: Double?,
    val muscleMass: Double?,
    val bmi: Double?
)

data class NutritionData(
    val calories: Double,
    val carbs: Double?,
    val protein: Double?,
    val fat: Double?,
    val fiber: Double?
)

data class WaterIntakeData(
    val amountMl: Double,
    val goalMl: Double
)

data class FloorData(
    val climbed: Int,
    val goal: Int
)

data class WorkoutData(
    val type: String,
    val durationMin: Int,
    val calories: Double,
    val start: String,
    val end: String,
    val avgHR: Int?,
    val distanceM: Double?
)
