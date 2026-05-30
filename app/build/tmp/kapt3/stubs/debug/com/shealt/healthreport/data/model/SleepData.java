package com.shealt.healthreport.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003JB\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/shealt/healthreport/data/model/SleepData;", "", "totalMinutes", "", "score", "startTime", "Ljava/time/LocalDateTime;", "endTime", "stages", "Lcom/shealt/healthreport/data/model/SleepStages;", "(ILjava/lang/Integer;Ljava/time/LocalDateTime;Ljava/time/LocalDateTime;Lcom/shealt/healthreport/data/model/SleepStages;)V", "getEndTime", "()Ljava/time/LocalDateTime;", "getScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getStages", "()Lcom/shealt/healthreport/data/model/SleepStages;", "getStartTime", "getTotalMinutes", "()I", "component1", "component2", "component3", "component4", "component5", "copy", "(ILjava/lang/Integer;Ljava/time/LocalDateTime;Ljava/time/LocalDateTime;Lcom/shealt/healthreport/data/model/SleepStages;)Lcom/shealt/healthreport/data/model/SleepData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class SleepData {
    private final int totalMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer score = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDateTime startTime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDateTime endTime = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.data.model.SleepStages stages = null;
    
    public SleepData(int totalMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer score, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime startTime, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime endTime, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.model.SleepStages stages) {
        super();
    }
    
    public final int getTotalMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getScore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime getStartTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime getEndTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.shealt.healthreport.data.model.SleepStages getStages() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.shealt.healthreport.data.model.SleepStages component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.shealt.healthreport.data.model.SleepData copy(int totalMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer score, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime startTime, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime endTime, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.model.SleepStages stages) {
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