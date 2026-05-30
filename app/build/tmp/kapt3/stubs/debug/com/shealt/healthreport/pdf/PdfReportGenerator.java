package com.shealt.healthreport.pdf;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fH\u0002J\u0018\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J&\u0010\u0014\u001a\u0004\u0018\u00010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0011\u001a\u00020\u0012J\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/shealt/healthreport/pdf/PdfReportGenerator;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "appendDataPages", "", "document", "Landroid/graphics/pdf/PdfDocument;", "jsonString", "", "createGson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "drawPage1", "canvas", "Landroid/graphics/Canvas;", "report", "Lcom/shealt/healthreport/data/model/DailyHealthReport;", "drawPage2", "generateMultipleDaysPdf", "Ljava/io/File;", "reports", "", "startDate", "Ljava/time/LocalDate;", "endDate", "generatePdf", "savePdf", "translateMealType", "type", "app_debug"})
public final class PdfReportGenerator {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public PdfReportGenerator(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File generatePdf(@org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.model.DailyHealthReport report) {
        return null;
    }
    
    private final void drawPage1(android.graphics.Canvas canvas, com.shealt.healthreport.data.model.DailyHealthReport report) {
    }
    
    private final void drawPage2(android.graphics.Canvas canvas, com.shealt.healthreport.data.model.DailyHealthReport report) {
    }
    
    private final java.lang.String translateMealType(java.lang.String type) {
        return null;
    }
    
    private final java.io.File savePdf(android.graphics.pdf.PdfDocument document, com.shealt.healthreport.data.model.DailyHealthReport report) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File generateMultipleDaysPdf(@org.jetbrains.annotations.NotNull()
    java.util.List<com.shealt.healthreport.data.model.DailyHealthReport> reports, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
        return null;
    }
    
    private final void appendDataPages(android.graphics.pdf.PdfDocument document, java.lang.String jsonString) {
    }
    
    private final com.google.gson.Gson createGson() {
        return null;
    }
}