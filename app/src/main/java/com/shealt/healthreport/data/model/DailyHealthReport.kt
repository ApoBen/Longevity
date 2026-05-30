package com.shealt.healthreport.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyHealthReport(
    val date: LocalDate,
    val sleep: SleepData? = null,
    val energy: EnergyData? = null,
    val heartRate: HeartRateData? = null,
    val steps: StepData? = null,
    val calories: CalorieData? = null,
    val workouts: List<WorkoutData> = emptyList(),
    val bloodPressure: List<BloodPressureData> = emptyList(),
    val bloodOxygen: List<BloodOxygenData> = emptyList(),
    val bloodGlucose: List<BloodGlucoseData> = emptyList(),
    val bodyComposition: BodyCompositionData? = null,
    val nutrition: NutritionData? = null,
    val waterIntake: WaterIntakeData? = null,
    val floors: FloorData? = null,
    val skinTemperature: List<SkinTemperatureData> = emptyList(),
    val sleepApnea: SleepApneaData? = null,
    val userProfile: UserProfileData? = null
)

data class SleepData(
    val totalDurationMinutes: Int,
    val sleepScore: Int?, // Samsung Sleep Score
    val remMinutes: Int,
    val lightSleepMinutes: Int,
    val deepSleepMinutes: Int,
    val awakeMinutes: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)

data class EnergyData(
    val score: Int, // Samsung Energy Score (0-100)
    val physicalActivityScore: Int?,
    val sleepScore: Int?,
    val heartRateScore: Int?
)

data class HeartRateData(
    val averageBpm: Int,
    val minBpm: Int,
    val maxBpm: Int,
    val restingBpm: Int?
)

data class StepData(
    val totalSteps: Int,
    val goalSteps: Int,
    val distanceMeters: Double
)

data class CalorieData(
    val totalCalories: Double,
    val activeCalories: Double,
    val restCalories: Double
)

data class WorkoutData(
    val type: String, // E.g., Running, Walking, Swimming
    val durationMinutes: Int,
    val caloriesBurned: Double,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val averageHeartRate: Int?,
    val distanceMeters: Double?
)

data class BloodPressureData(
    val systolic: Double,
    val diastolic: Double,
    val pulse: Int?,
    val timestamp: LocalDateTime
)

data class BloodOxygenData(
    val spo2: Double,
    val timestamp: LocalDateTime
)

data class BloodGlucoseData(
    val glucose: Double,
    val mealType: String?, // e.g., Fasting, After Meal
    val timestamp: LocalDateTime
)

data class BodyCompositionData(
    val weightKg: Double,
    val heightCm: Double?,
    val bodyFatPercentage: Double?,
    val skeletalMuscleMassKg: Double?,
    val bmi: Double?
)

data class NutritionData(
    val calories: Double,
    val carbohydratesGrams: Double?,
    val proteinGrams: Double?,
    val fatGrams: Double?,
    val fiberGrams: Double?
)

data class WaterIntakeData(
    val amountMl: Double,
    val goalMl: Double
)

data class FloorData(
    val floorsClimbed: Int,
    val goalFloors: Int
)

data class SkinTemperatureData(
    val temperatureCelsius: Double,
    val timestamp: LocalDateTime
)

data class SleepApneaData(
    val averageAhi: Double, // Apnea-Hypopnea Index
    val severity: String,
    val timestamp: LocalDateTime
)

data class UserProfileData(
    val nickname: String?,
    val gender: String?,
    val birthDate: String?,
    val heightCm: Double?,
    val weightKg: Double?
)
