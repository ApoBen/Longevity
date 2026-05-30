package com.shealt.healthreport.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u00c1\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0011\u00a2\u0006\u0002\u0010!J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0016H\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u001aH\u00c6\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u001cH\u00c6\u0003J\u0010\u0010D\u001a\u0004\u0018\u00010\u001eH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\u000f\u0010E\u001a\b\u0012\u0004\u0012\u00020 0\u0011H\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\u000f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011H\u00c6\u0003J\u00cc\u0001\u0010N\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00112\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0011H\u00c6\u0001\u00a2\u0006\u0002\u0010OJ\u0013\u0010P\u001a\u00020Q2\b\u0010R\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010S\u001a\u00020\u001eH\u00d6\u0001J\t\u0010T\u001a\u00020UH\u00d6\u0001R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010#R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b-\u0010.R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010#\u00a8\u0006V"}, d2 = {"Lcom/shealt/healthreport/data/model/DailyHealthReport;", "", "date", "Ljava/time/LocalDate;", "heartRate", "Lcom/shealt/healthreport/data/model/HeartRateData;", "bloodOxygen", "Lcom/shealt/healthreport/data/model/BloodOxygenData;", "steps", "Lcom/shealt/healthreport/data/model/StepData;", "skinTemperature", "Lcom/shealt/healthreport/data/model/SkinTemperatureData;", "sleep", "Lcom/shealt/healthreport/data/model/SleepData;", "calories", "Lcom/shealt/healthreport/data/model/CalorieData;", "bloodPressure", "", "Lcom/shealt/healthreport/data/model/BloodPressureData;", "bloodGlucose", "Lcom/shealt/healthreport/data/model/BloodGlucoseData;", "bodyComposition", "Lcom/shealt/healthreport/data/model/BodyCompositionData;", "nutrition", "Lcom/shealt/healthreport/data/model/NutritionData;", "waterIntake", "Lcom/shealt/healthreport/data/model/WaterIntakeData;", "floors", "Lcom/shealt/healthreport/data/model/FloorData;", "energyScore", "", "workouts", "Lcom/shealt/healthreport/data/model/WorkoutData;", "(Ljava/time/LocalDate;Lcom/shealt/healthreport/data/model/HeartRateData;Lcom/shealt/healthreport/data/model/BloodOxygenData;Lcom/shealt/healthreport/data/model/StepData;Lcom/shealt/healthreport/data/model/SkinTemperatureData;Lcom/shealt/healthreport/data/model/SleepData;Lcom/shealt/healthreport/data/model/CalorieData;Ljava/util/List;Ljava/util/List;Lcom/shealt/healthreport/data/model/BodyCompositionData;Lcom/shealt/healthreport/data/model/NutritionData;Lcom/shealt/healthreport/data/model/WaterIntakeData;Lcom/shealt/healthreport/data/model/FloorData;Ljava/lang/Integer;Ljava/util/List;)V", "getBloodGlucose", "()Ljava/util/List;", "getBloodOxygen", "()Lcom/shealt/healthreport/data/model/BloodOxygenData;", "getBloodPressure", "getBodyComposition", "()Lcom/shealt/healthreport/data/model/BodyCompositionData;", "getCalories", "()Lcom/shealt/healthreport/data/model/CalorieData;", "getDate", "()Ljava/time/LocalDate;", "getEnergyScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getFloors", "()Lcom/shealt/healthreport/data/model/FloorData;", "getHeartRate", "()Lcom/shealt/healthreport/data/model/HeartRateData;", "getNutrition", "()Lcom/shealt/healthreport/data/model/NutritionData;", "getSkinTemperature", "()Lcom/shealt/healthreport/data/model/SkinTemperatureData;", "getSleep", "()Lcom/shealt/healthreport/data/model/SleepData;", "getSteps", "()Lcom/shealt/healthreport/data/model/StepData;", "getWaterIntake", "()Lcom/shealt/healthreport/data/model/WaterIntakeData;", "getWorkouts", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/time/LocalDate;Lcom/shealt/healthreport/data/model/HeartRateData;Lcom/shealt/healthreport/data/model/BloodOxygenData;Lcom/shealt/healthreport/data/model/StepData;Lcom/shealt/healthreport/data/model/SkinTemperatureData;Lcom/shealt/healthreport/data/model/SleepData;Lcom/shealt/healthreport/data/model/CalorieData;Ljava/util/List;Ljava/util/List;Lcom/shealt/healthreport/data/model/BodyCompositionData;Lcom/shealt/healthreport/data/model/NutritionData;Lcom/shealt/healthreport/data/model/WaterIntakeData;Lcom/shealt/healthreport/data/model/FloorData;Ljava/lang/Integer;Ljava/util/List;)Lcom/shealt/healthreport/data/model/DailyHealthReport;", "equals", "", "other", "hashCode", "toString", "", "app_release"})
public final class DailyHealthReport {
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate date = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.HeartRateData heartRate = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.BloodOxygenData bloodOxygen = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.StepData steps = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.SkinTemperatureData skinTemperature = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.SleepData sleep = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.CalorieData calories = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.shealt.healthreport.data.model.BloodPressureData> bloodPressure = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData> bloodGlucose = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.BodyCompositionData bodyComposition = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.NutritionData nutrition = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.WaterIntakeData waterIntake = null;
    @org.jetbrains.annotations.Nullable()
    private final com.shealt.healthreport.data.model.FloorData floors = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer energyScore = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.shealt.healthreport.data.model.WorkoutData> workouts = null;
    
    public DailyHealthReport(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.HeartRateData heartRate, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.BloodOxygenData bloodOxygen, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.StepData steps, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.SkinTemperatureData skinTemperature, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.SleepData sleep, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.CalorieData calories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.BloodPressureData> bloodPressure, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData> bloodGlucose, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.BodyCompositionData bodyComposition, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.NutritionData nutrition, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.WaterIntakeData waterIntake, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.FloorData floors, @org.jetbrains.annotations.Nullable()
    java.lang.Integer energyScore, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.WorkoutData> workouts) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.HeartRateData getHeartRate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.BloodOxygenData getBloodOxygen() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.StepData getSteps() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.SkinTemperatureData getSkinTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.SleepData getSleep() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.CalorieData getCalories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.BloodPressureData> getBloodPressure() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData> getBloodGlucose() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.BodyCompositionData getBodyComposition() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.NutritionData getNutrition() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.WaterIntakeData getWaterIntake() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.FloorData getFloors() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getEnergyScore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.WorkoutData> getWorkouts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.BodyCompositionData component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.NutritionData component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.WaterIntakeData component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.FloorData component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.WorkoutData> component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.HeartRateData component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.BloodOxygenData component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.StepData component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.SkinTemperatureData component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.SleepData component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.shealt.healthreport.data.model.CalorieData component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.BloodPressureData> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.shealt.healthreport.data.model.DailyHealthReport copy(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.HeartRateData heartRate, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.BloodOxygenData bloodOxygen, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.StepData steps, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.SkinTemperatureData skinTemperature, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.SleepData sleep, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.CalorieData calories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.BloodPressureData> bloodPressure, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.BloodGlucoseData> bloodGlucose, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.BodyCompositionData bodyComposition, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.NutritionData nutrition, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.WaterIntakeData waterIntake, @org.jetbrains.annotations.Nullable()
    com.shealt.healthreport.data.model.FloorData floors, @org.jetbrains.annotations.Nullable()
    java.lang.Integer energyScore, @org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.WorkoutData> workouts) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}