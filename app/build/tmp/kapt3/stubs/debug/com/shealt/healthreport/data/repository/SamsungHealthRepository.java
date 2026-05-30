package com.shealt.healthreport.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00062\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020%H\u0082@\u00a2\u0006\u0002\u0010&J \u0010\'\u001a\u0004\u0018\u00010(2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010)J\u001c\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00062\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010,\u001a\u00020-2\u0006\u0010$\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/shealt/healthreport/data/repository/SamsungHealthRepository;", "", "healthDataStore", "Lcom/samsung/android/sdk/health/data/HealthDataStore;", "(Lcom/samsung/android/sdk/health/data/HealthDataStore;)V", "fetchBloodGlucose", "", "Lcom/shealt/healthreport/data/model/BloodGlucoseData;", "timeFilter", "Lcom/samsung/android/sdk/health/data/request/LocalTimeFilter;", "(Lcom/samsung/android/sdk/health/data/request/LocalTimeFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchBloodOxygen", "Lcom/shealt/healthreport/data/model/BloodOxygenData;", "fetchBloodPressure", "Lcom/shealt/healthreport/data/model/BloodPressureData;", "fetchBodyComposition", "Lcom/shealt/healthreport/data/model/BodyCompositionData;", "fetchCalorieData", "Lcom/shealt/healthreport/data/model/CalorieData;", "fetchEnergyData", "", "dateFilter", "Lcom/samsung/android/sdk/health/data/request/LocalDateFilter;", "(Lcom/samsung/android/sdk/health/data/request/LocalDateFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchFloorData", "Lcom/shealt/healthreport/data/model/FloorData;", "fetchHeartRateData", "Lcom/shealt/healthreport/data/model/HeartRateData;", "fetchNutritionData", "Lcom/shealt/healthreport/data/model/NutritionData;", "fetchSkinTemperature", "Lcom/shealt/healthreport/data/model/SkinTemperatureData;", "fetchSleepData", "Lcom/shealt/healthreport/data/model/SleepData;", "fetchStepsData", "Lcom/shealt/healthreport/data/model/StepData;", "date", "Ljava/time/LocalDate;", "(Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchWaterIntake", "Lcom/shealt/healthreport/data/model/WaterIntakeData;", "(Lcom/samsung/android/sdk/health/data/request/LocalTimeFilter;Lcom/samsung/android/sdk/health/data/request/LocalDateFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchWorkouts", "Lcom/shealt/healthreport/data/model/WorkoutData;", "getDailyReport", "Lcom/shealt/healthreport/data/model/DailyHealthReport;", "Companion", "app_debug"})
public final class SamsungHealthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.samsung.android.sdk.health.data.HealthDataStore healthDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SamsungHealthRepository";
    private static final java.time.format.DateTimeFormatter TIME_FORMATTER = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.shealt.healthreport.data.repository.SamsungHealthRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public SamsungHealthRepository(@org.jetbrains.annotations.NotNull()
    com.samsung.android.sdk.health.data.HealthDataStore healthDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDailyReport(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.DailyHealthReport> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchSleepData(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.SleepData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchStepsData(java.time.LocalDate date, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.StepData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchHeartRateData(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.HeartRateData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchEnergyData(com.samsung.android.sdk.health.data.request.LocalDateFilter dateFilter, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchWorkouts(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super java.util.List<com.shealt.healthreport.data.model.WorkoutData>> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchCalorieData(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.CalorieData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchBloodPressure(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super java.util.List<com.shealt.healthreport.data.model.BloodPressureData>> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchBloodOxygen(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.BloodOxygenData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchBloodGlucose(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData>> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchBodyComposition(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.BodyCompositionData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchNutritionData(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.NutritionData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchWaterIntake(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, com.samsung.android.sdk.health.data.request.LocalDateFilter dateFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.WaterIntakeData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchFloorData(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.FloorData> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchSkinTemperature(com.samsung.android.sdk.health.data.request.LocalTimeFilter timeFilter, kotlin.coroutines.Continuation<? super com.shealt.healthreport.data.model.SkinTemperatureData> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/shealt/healthreport/data/repository/SamsungHealthRepository$Companion;", "", "()V", "TAG", "", "TIME_FORMATTER", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}