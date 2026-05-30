package com.shealt.healthreport.data.repository

import android.util.Log
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.data.entries.SleepSession
import com.samsung.android.sdk.health.data.request.DataTypes
import com.samsung.android.sdk.health.data.request.DataType
import com.samsung.android.sdk.health.data.request.DataType.SleepType.StageType
import com.samsung.android.sdk.health.data.request.DataType.BloodGlucoseType.MealStatus
import com.samsung.android.sdk.health.data.request.LocalTimeFilter
import com.samsung.android.sdk.health.data.request.LocalDateFilter
import com.samsung.android.sdk.health.data.helper.read
import com.samsung.android.sdk.health.data.helper.aggregate
import com.shealt.healthreport.data.model.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SamsungHealthRepository @Inject constructor(
    private val healthDataStore: HealthDataStore
) {
    companion object {
        private const val TAG = "SamsungHealthRepository"
        private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
    }

    suspend fun getDailyReport(date: LocalDate): DailyHealthReport {
        val startOfDay = date.atStartOfDay()
        val endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1)
        val timeFilter = LocalTimeFilter.of(startOfDay, endOfDay)
        val dateFilter = LocalDateFilter.of(date, date)

        val sleepData = fetchSleepData(timeFilter)
        val stepsData = fetchStepsData(date)
        val heartRateData = fetchHeartRateData(timeFilter)
        val energyData = fetchEnergyData(dateFilter)
        val workouts = fetchWorkouts(timeFilter)
        val calories = fetchCalorieData(timeFilter)
        val bloodPressure = fetchBloodPressure(timeFilter)
        val bloodOxygen = fetchBloodOxygen(timeFilter)
        val bloodGlucose = fetchBloodGlucose(timeFilter)
        val bodyComposition = fetchBodyComposition(timeFilter)
        val nutrition = fetchNutritionData(timeFilter)
        val waterIntake = fetchWaterIntake(timeFilter, dateFilter)
        val floors = fetchFloorData(timeFilter)
        val skinTemperature = fetchSkinTemperature(timeFilter)

        return DailyHealthReport(
            date = date,
            sleep = sleepData,
            steps = stepsData,
            heartRate = heartRateData,
            bloodOxygen = bloodOxygen,
            skinTemperature = skinTemperature,
            calories = calories,
            bloodPressure = bloodPressure,
            bloodGlucose = bloodGlucose,
            bodyComposition = bodyComposition,
            nutrition = nutrition,
            waterIntake = waterIntake,
            floors = floors,
            energyScore = energyData,
            workouts = workouts
        )
    }

    private suspend fun fetchSleepData(timeFilter: LocalTimeFilter): SleepData? {
        return try {
            val response = healthDataStore.read(DataTypes.SLEEP) {
                setLocalTimeFilter(timeFilter)
            }
            val data = response.dataList.firstOrNull() ?: return null
            
            val duration = data.getValueOrDefault(DataType.SleepType.DURATION, java.time.Duration.ZERO)?.toMinutes()?.toInt() ?: 0
            val score = data.getValueOrDefault(DataType.SleepType.SLEEP_SCORE, 0) ?: 0
            
            var rem = 0
            var light = 0
            var deep = 0
            var awake = 0
            
            val sessions = data.getValueOrDefault(DataType.SleepType.SESSIONS, emptyList()) ?: emptyList()
            sessions.forEach { session ->
                session.stages?.forEach { stage ->
                    val stageDuration = java.time.Duration.between(stage.startTime, stage.endTime).toMinutes().toInt()
                    when (stage.stage) {
                        StageType.REM -> rem += stageDuration
                        StageType.LIGHT -> light += stageDuration
                        StageType.DEEP -> deep += stageDuration
                        StageType.AWAKE -> awake += stageDuration
                        else -> {}
                    }
                }
            }

            SleepData(
                totalMinutes = duration,
                score = if (score > 0) score else null,
                startTime = LocalDateTime.ofInstant(data.startTime, ZoneId.systemDefault()),
                endTime = LocalDateTime.ofInstant(data.endTime, ZoneId.systemDefault()),
                stages = SleepStages(rem = rem, light = light, deep = deep, awake = awake)
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchSleepData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchStepsData(date: LocalDate): StepData? {
        return try {
            val startOfDay = date.atStartOfDay()
            val endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1)
            val timeFilter = LocalTimeFilter.of(startOfDay, endOfDay)
            val dateFilter = LocalDateFilter.of(date, date)

            val totalStepsResponse = healthDataStore.aggregate(DataType.StepsType.TOTAL) {
                setLocalTimeFilter(timeFilter)
            }
            val totalSteps = totalStepsResponse.dataList.firstOrNull()?.getValueOrDefault(0L)?.toInt() ?: 0

            val goalSteps = try {
                val goalResponse = healthDataStore.aggregate(DataType.StepsGoalType.LAST) {
                    setLocalDateFilter(dateFilter)
                }
                goalResponse.dataList.firstOrNull()?.getValueOrDefault(0)?.toInt() ?: 6000
            } catch (e: Exception) {
                6000
            }

            val distance = try {
                val distanceResponse = healthDataStore.aggregate(DataType.ActivitySummaryType.TOTAL_DISTANCE) {
                    setLocalTimeFilter(timeFilter)
                }
                distanceResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toDouble() ?: 0.0
            } catch (e: Exception) {
                0.0
            }

            val hourlyData = mutableListOf<HourlySteps>()
            
            for (hour in 0..23) {
                val startOfHour = date.atTime(hour, 0)
                val endOfHour = startOfHour.plusHours(1).minusNanos(1)
                val hourFilter = LocalTimeFilter.of(startOfHour, endOfHour)
                
                try {
                    val hourResponse = healthDataStore.aggregate(DataType.StepsType.TOTAL) {
                        setLocalTimeFilter(hourFilter)
                    }
                    val count = hourResponse.dataList.firstOrNull()?.getValueOrDefault(0L)?.toInt() ?: 0
                    if (count > 0) {
                        hourlyData.add(HourlySteps(hour = hour, steps = count))
                    }
                } catch (e: Exception) {
                    // Ignore hour if failed
                }
            }

            StepData(
                total = totalSteps,
                goal = goalSteps,
                distanceMeters = distance,
                hourly = hourlyData
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchStepsData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchHeartRateData(timeFilter: LocalTimeFilter): HeartRateData? {
        return try {
            val response = healthDataStore.read(DataTypes.HEART_RATE) {
                setLocalTimeFilter(timeFilter)
            }
            if (response.dataList.isEmpty()) return null

            var min = Float.MAX_VALUE
            var max = Float.MIN_VALUE
            var sum = 0f
            var count = 0
            val hourlyMap = mutableMapOf<Int, MutableList<Float>>()

            response.dataList.forEach { point ->
                val rate = point.getValueOrDefault(DataType.HeartRateType.HEART_RATE, 0f) ?: 0f
                if (rate > 0) {
                    if (rate < min) min = rate
                    if (rate > max) max = rate
                    sum += rate
                    count++
                    
                    val hour = LocalDateTime.ofInstant(point.startTime, ZoneId.systemDefault()).hour
                    hourlyMap.getOrPut(hour) { mutableListOf() }.add(rate)
                }
            }
            
            val hourlyData = (0..23).mapNotNull { hour ->
                val readings = hourlyMap[hour]
                if (!readings.isNullOrEmpty()) {
                    HourlyHeartRate(
                        hour = hour,
                        min = readings.minOrNull()?.toInt() ?: 0,
                        max = readings.maxOrNull()?.toInt() ?: 0,
                        avg = readings.average().toInt(),
                        count = readings.size
                    )
                } else null
            }

            if (count == 0) return null

            HeartRateData(
                dailySummary = HeartRateSummary(
                    avg = (sum / count).toInt(),
                    min = min.toInt(),
                    max = max.toInt(),
                    resting = min.toInt() // estimate
                ),
                hourly = hourlyData
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchHeartRateData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchEnergyData(dateFilter: LocalDateFilter): Int? {
        return try {
            val response = healthDataStore.read(DataTypes.ENERGY_SCORE) {
                setLocalDateFilter(dateFilter)
            }
            response.dataList.firstOrNull()?.getValueOrDefault(DataType.EnergyScoreType.ENERGY_SCORE, 0f)?.toInt()
        } catch (e: Exception) {
            Log.e(TAG, "fetchEnergyData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchWorkouts(timeFilter: LocalTimeFilter): List<WorkoutData> {
        return try {
            val response = healthDataStore.read(DataTypes.EXERCISE) {
                setLocalTimeFilter(timeFilter)
            }
            val workouts = mutableListOf<WorkoutData>()
            response.dataList.forEach { point ->
                val sessions = point.getValueOrDefault(DataType.ExerciseType.SESSIONS, emptyList()) ?: emptyList()
                sessions.forEach { session ->
                    workouts.add(WorkoutData(
                        type = session.customTitle ?: "Workout",
                        durationMin = session.duration?.toMinutes()?.toInt() ?: 0,
                        calories = session.calories?.toDouble() ?: 0.0,
                        start = LocalDateTime.ofInstant(session.startTime, ZoneId.systemDefault()).format(TIME_FORMATTER),
                        end = LocalDateTime.ofInstant(session.endTime, ZoneId.systemDefault()).format(TIME_FORMATTER),
                        avgHR = null,
                        distanceM = session.distance?.toDouble()
                    ))
                }
            }
            workouts
        } catch (e: Exception) {
            Log.e(TAG, "fetchWorkouts failed: ${e.message}", e)
            emptyList()
        }
    }

    private suspend fun fetchCalorieData(timeFilter: LocalTimeFilter): CalorieData? {
        return try {
            val activeCalories = try {
                val activeResponse = healthDataStore.aggregate(DataType.ActivitySummaryType.TOTAL_ACTIVE_CALORIES_BURNED) {
                    setLocalTimeFilter(timeFilter)
                }
                activeResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toDouble() ?: 0.0
            } catch (e: Exception) { 0.0 }

            val totalCalories = try {
                val totalResponse = healthDataStore.aggregate(DataType.ActivitySummaryType.TOTAL_CALORIES_BURNED) {
                    setLocalTimeFilter(timeFilter)
                }
                totalResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toDouble() ?: 0.0
            } catch (e: Exception) { 0.0 }

            val restCalories = if (totalCalories > activeCalories) totalCalories - activeCalories else 0.0

            CalorieData(
                total = totalCalories,
                active = activeCalories,
                rest = restCalories
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchCalorieData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchBloodPressure(timeFilter: LocalTimeFilter): List<BloodPressureData> {
        return try {
            val response = healthDataStore.read(DataTypes.BLOOD_PRESSURE) {
                setLocalTimeFilter(timeFilter)
            }
            response.dataList.map { point ->
                val systolic = point.getValueOrDefault(DataType.BloodPressureType.SYSTOLIC, 0f).toDouble()
                val diastolic = point.getValueOrDefault(DataType.BloodPressureType.DIASTOLIC, 0f).toDouble()
                val pulse = point.getValueOrDefault(DataType.BloodPressureType.PULSE_RATE, 0)
                BloodPressureData(
                    systolic = systolic,
                    diastolic = diastolic,
                    pulse = if (pulse > 0) pulse else null,
                    time = LocalDateTime.ofInstant(point.startTime, ZoneId.systemDefault()).format(TIME_FORMATTER)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchBloodPressure failed: ${e.message}", e)
            emptyList()
        }
    }

    private suspend fun fetchBloodOxygen(timeFilter: LocalTimeFilter): BloodOxygenData? {
        return try {
            val response = healthDataStore.read(DataTypes.BLOOD_OXYGEN) {
                setLocalTimeFilter(timeFilter)
            }
            if (response.dataList.isEmpty()) return null
            
            val hourlyMap = mutableMapOf<Int, MutableList<Double>>()
            
            response.dataList.forEach { point ->
                val spo2 = point.getValueOrDefault(DataType.BloodOxygenType.OXYGEN_SATURATION, 0f).toDouble()
                if (spo2 > 0) {
                    val hour = LocalDateTime.ofInstant(point.startTime, ZoneId.systemDefault()).hour
                    hourlyMap.getOrPut(hour) { mutableListOf() }.add(spo2)
                }
            }
            
            val hourlyData = hourlyMap.map { (hour, readings) ->
                HourlyBloodOxygen(
                    hour = hour,
                    avg = readings.average(),
                    min = readings.minOrNull() ?: 0.0,
                    count = readings.size
                )
            }.sortedBy { it.hour }
            
            BloodOxygenData(hourly = hourlyData)
        } catch (e: Exception) {
            Log.e(TAG, "fetchBloodOxygen failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchBloodGlucose(timeFilter: LocalTimeFilter): List<BloodGlucoseData> {
        return try {
            val response = healthDataStore.read(DataTypes.BLOOD_GLUCOSE) {
                setLocalTimeFilter(timeFilter)
            }
            response.dataList.map { point ->
                val glucose = point.getValueOrDefault(DataType.BloodGlucoseType.GLUCOSE_LEVEL, 0f).toDouble()
                val mealStatus = point.getValue(DataType.BloodGlucoseType.MEAL_STATUS)
                BloodGlucoseData(
                    glucose = glucose,
                    mealType = mealStatus?.name,
                    time = LocalDateTime.ofInstant(point.startTime, ZoneId.systemDefault()).format(TIME_FORMATTER)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchBloodGlucose failed: ${e.message}", e)
            emptyList()
        }
    }

    private suspend fun fetchBodyComposition(timeFilter: LocalTimeFilter): BodyCompositionData? {
        return try {
            val response = healthDataStore.read(DataTypes.BODY_COMPOSITION) {
                setLocalTimeFilter(timeFilter)
            }
            val point = response.dataList.firstOrNull() ?: return null
            val weight = point.getValueOrDefault(DataType.BodyCompositionType.WEIGHT, 0f).toDouble()
            val fat = point.getValueOrDefault(DataType.BodyCompositionType.BODY_FAT, 0f).toDouble()
            val muscle = point.getValueOrDefault(DataType.BodyCompositionType.SKELETAL_MUSCLE_MASS, 0f).toDouble()
            val bmi = point.getValueOrDefault(DataType.BodyCompositionType.BODY_MASS_INDEX, 0f).toDouble()

            BodyCompositionData(
                weightKg = weight,
                bodyFat = if (fat > 0.0) fat else null,
                muscleMass = if (muscle > 0.0) muscle else null,
                bmi = if (bmi > 0.0) bmi else null
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchBodyComposition failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchNutritionData(timeFilter: LocalTimeFilter): NutritionData? {
        return try {
            val response = healthDataStore.read(DataTypes.NUTRITION) {
                setLocalTimeFilter(timeFilter)
            }
            if (response.dataList.isEmpty()) return null

            var totalCalories = 0.0
            var totalCarbs = 0.0
            var totalProtein = 0.0
            var totalFat = 0.0
            var totalFiber = 0.0

            response.dataList.forEach { point ->
                totalCalories += point.getValueOrDefault(DataType.NutritionType.CALORIES, 0f).toDouble()
                totalCarbs += point.getValueOrDefault(DataType.NutritionType.CARBOHYDRATE, 0f).toDouble()
                totalProtein += point.getValueOrDefault(DataType.NutritionType.PROTEIN, 0f).toDouble()
                totalFat += point.getValueOrDefault(DataType.NutritionType.TOTAL_FAT, 0f).toDouble()
                totalFiber += point.getValueOrDefault(DataType.NutritionType.DIETARY_FIBER, 0f).toDouble()
            }

            NutritionData(
                calories = totalCalories,
                carbs = totalCarbs,
                protein = totalProtein,
                fat = totalFat,
                fiber = totalFiber
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchNutritionData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchWaterIntake(timeFilter: LocalTimeFilter, dateFilter: LocalDateFilter): WaterIntakeData? {
        return try {
            val totalWater = try {
                val waterResponse = healthDataStore.aggregate(DataType.WaterIntakeType.TOTAL) {
                    setLocalTimeFilter(timeFilter)
                }
                waterResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toDouble() ?: 0.0
            } catch (e: Exception) { 0.0 }

            val goalWater = try {
                val goalResponse = healthDataStore.aggregate(DataType.WaterIntakeGoalType.LAST) {
                    setLocalDateFilter(dateFilter)
                }
                goalResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toDouble() ?: 2000.0
            } catch (e: Exception) { 2000.0 }

            WaterIntakeData(
                amountMl = totalWater,
                goalMl = goalWater
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchWaterIntake failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchFloorData(timeFilter: LocalTimeFilter): FloorData? {
        return try {
            val totalFloors = try {
                val floorsResponse = healthDataStore.aggregate(DataType.FloorsClimbedType.TOTAL) {
                    setLocalTimeFilter(timeFilter)
                }
                floorsResponse.dataList.firstOrNull()?.getValueOrDefault(0f)?.toInt() ?: 0
            } catch (e: Exception) { 0 }

            FloorData(
                climbed = totalFloors,
                goal = 10
            )
        } catch (e: Exception) {
            Log.e(TAG, "fetchFloorData failed: ${e.message}", e)
            null
        }
    }

    private suspend fun fetchSkinTemperature(timeFilter: LocalTimeFilter): SkinTemperatureData? {
        return try {
            val response = healthDataStore.read(DataTypes.SKIN_TEMPERATURE) {
                setLocalTimeFilter(timeFilter)
            }
            if (response.dataList.isEmpty()) return null
            
            val hourlyMap = mutableMapOf<Int, MutableList<Double>>()
            
            response.dataList.forEach { point ->
                val temp = point.getValueOrDefault(DataType.SkinTemperatureType.SKIN_TEMPERATURE, 0f).toDouble()
                if (temp > 0) {
                    val hour = LocalDateTime.ofInstant(point.startTime, ZoneId.systemDefault()).hour
                    hourlyMap.getOrPut(hour) { mutableListOf() }.add(temp)
                }
            }
            
            val hourlyData = hourlyMap.map { (hour, readings) ->
                HourlySkinTemperature(
                    hour = hour,
                    avg = readings.average(),
                    count = readings.size
                )
            }.sortedBy { it.hour }
            
            SkinTemperatureData(hourly = hourlyData)
        } catch (e: Exception) {
            Log.e(TAG, "fetchSkinTemperature failed: ${e.message}", e)
            null
        }
    }
}
