package com.shealt.healthreport.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/shealt/healthreport/data/repository/HealthPermissionManager;", "", "healthDataStore", "Lcom/samsung/android/sdk/health/data/HealthDataStore;", "(Lcom/samsung/android/sdk/health/data/HealthDataStore;)V", "requiredPermissions", "", "Lcom/samsung/android/sdk/health/data/permission/Permission;", "hasAllPermissions", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestPermissions", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class HealthPermissionManager {
    @org.jetbrains.annotations.NotNull()
    private final com.samsung.android.sdk.health.data.HealthDataStore healthDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "HealthPermissionManager";
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.samsung.android.sdk.health.data.permission.Permission> requiredPermissions = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.shealt.healthreport.data.repository.HealthPermissionManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public HealthPermissionManager(@org.jetbrains.annotations.NotNull()
    com.samsung.android.sdk.health.data.HealthDataStore healthDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasAllPermissions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object requestPermissions(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/shealt/healthreport/data/repository/HealthPermissionManager$Companion;", "", "()V", "TAG", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}