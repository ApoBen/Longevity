package com.shealt.healthreport.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 J2\u00020\u0001:\u0001JB?\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u0006\u00100\u001a\u000201J\u0006\u00102\u001a\u000201J\u0006\u00103\u001a\u000201J\u0006\u00104\u001a\u000201J\u000e\u00105\u001a\u0002012\u0006\u00106\u001a\u00020,J\u0016\u00107\u001a\u0002012\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209J\u0006\u0010;\u001a\u000201J\u0006\u0010<\u001a\u000201J\u0010\u0010=\u001a\u00020\u00132\u0006\u0010>\u001a\u00020?H\u0002J\u000e\u0010@\u001a\u0002012\u0006\u0010A\u001a\u00020BJ\u000e\u0010C\u001a\u000201H\u0082@\u00a2\u0006\u0002\u0010DJ\u000e\u0010E\u001a\u0002012\u0006\u0010F\u001a\u00020\u0017J\u0016\u0010G\u001a\u0002012\u0006\u0010H\u001a\u00020&2\u0006\u0010I\u001a\u00020&R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0019\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0019\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00170\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00170\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001dR\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020&0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0+0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001dR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001dR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006K"}, d2 = {"Lcom/shealt/healthreport/ui/viewmodels/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "permissionManager", "Lcom/shealt/healthreport/data/repository/HealthPermissionManager;", "samsungHealthRepository", "Lcom/shealt/healthreport/data/repository/SamsungHealthRepository;", "pdfReportGenerator", "Lcom/shealt/healthreport/pdf/PdfReportGenerator;", "reportDao", "Lcom/shealt/healthreport/data/local/ReportDao;", "settingsDataStore", "Lcom/shealt/healthreport/data/local/SettingsDataStore;", "workScheduler", "Lcom/shealt/healthreport/worker/WorkScheduler;", "notificationHelper", "Lcom/shealt/healthreport/worker/NotificationHelper;", "(Lcom/shealt/healthreport/data/repository/HealthPermissionManager;Lcom/shealt/healthreport/data/repository/SamsungHealthRepository;Lcom/shealt/healthreport/pdf/PdfReportGenerator;Lcom/shealt/healthreport/data/local/ReportDao;Lcom/shealt/healthreport/data/local/SettingsDataStore;Lcom/shealt/healthreport/worker/WorkScheduler;Lcom/shealt/healthreport/worker/NotificationHelper;)V", "_errorMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_exportedFile", "Ljava/io/File;", "_hasPermissions", "", "_isGeneratingReport", "_statusMessage", "autoReportEnabled", "Lkotlinx/coroutines/flow/StateFlow;", "getAutoReportEnabled", "()Lkotlinx/coroutines/flow/StateFlow;", "errorMessage", "getErrorMessage", "exportedFile", "getExportedFile", "hasPermissions", "getHasPermissions", "isGeneratingReport", "reportHour", "", "getReportHour", "reportMinute", "getReportMinute", "reports", "", "Lcom/shealt/healthreport/data/local/ReportEntity;", "getReports", "statusMessage", "getStatusMessage", "checkPermissions", "", "clearError", "clearExportedFile", "clearStatus", "deleteReport", "report", "exportDateRange", "startDate", "Ljava/time/LocalDate;", "endDate", "exportPdfNow", "generateReportNow", "getReadableErrorMessage", "e", "", "requestPermissions", "activity", "Landroid/app/Activity;", "scheduleWorker", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setAutoReportEnabled", "enabled", "setReportTime", "hour", "minute", "Companion", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.data.repository.HealthPermissionManager permissionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.data.repository.SamsungHealthRepository samsungHealthRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.pdf.PdfReportGenerator pdfReportGenerator = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.data.local.ReportDao reportDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.data.local.SettingsDataStore settingsDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.worker.WorkScheduler workScheduler = null;
    @org.jetbrains.annotations.NotNull()
    private final com.shealt.healthreport.worker.NotificationHelper notificationHelper = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MainViewModel";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _hasPermissions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> hasPermissions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isGeneratingReport = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGeneratingReport = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.io.File> _exportedFile = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.io.File> exportedFile = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.shealt.healthreport.data.local.ReportEntity>> reports = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> autoReportEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> reportHour = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> reportMinute = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.shealt.healthreport.ui.viewmodels.MainViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.repository.HealthPermissionManager permissionManager, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.repository.SamsungHealthRepository samsungHealthRepository, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.pdf.PdfReportGenerator pdfReportGenerator, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.local.ReportDao reportDao, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.local.SettingsDataStore settingsDataStore, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.worker.WorkScheduler workScheduler, @org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.worker.NotificationHelper notificationHelper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getHasPermissions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGeneratingReport() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStatusMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.io.File> getExportedFile() {
        return null;
    }
    
    public final void clearExportedFile() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.shealt.healthreport.data.local.ReportEntity>> getReports() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getAutoReportEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getReportHour() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getReportMinute() {
        return null;
    }
    
    public final void checkPermissions() {
    }
    
    public final void requestPermissions(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void clearError() {
    }
    
    public final void clearStatus() {
    }
    
    public final void generateReportNow() {
    }
    
    public final void exportPdfNow() {
    }
    
    public final void deleteReport(@org.jetbrains.annotations.NotNull()
    com.shealt.healthreport.data.local.ReportEntity report) {
    }
    
    public final void setAutoReportEnabled(boolean enabled) {
    }
    
    public final void setReportTime(int hour, int minute) {
    }
    
    private final java.lang.Object scheduleWorker(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String getReadableErrorMessage(java.lang.Throwable e) {
        return null;
    }
    
    public final void exportDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/shealt/healthreport/ui/viewmodels/MainViewModel$Companion;", "", "()V", "TAG", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}