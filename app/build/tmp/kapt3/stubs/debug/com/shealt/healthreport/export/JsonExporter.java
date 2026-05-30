package com.shealt.healthreport.export;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006H\u0002J&\u0010\b\u001a\u0004\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/shealt/healthreport/export/JsonExporter;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "createGson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "exportMultipleReports", "Ljava/io/File;", "reports", "", "Lcom/shealt/healthreport/data/model/DailyHealthReport;", "startDate", "Ljava/time/LocalDate;", "endDate", "exportReport", "report", "app_debug"})
public final class JsonExporter {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public JsonExporter(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File exportReport(@org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.model.DailyHealthReport report) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File exportMultipleReports(@org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.DailyHealthReport> reports, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
        return null;
    }
    
    private final com.google.gson.Gson createGson() {
        return null;
    }
}