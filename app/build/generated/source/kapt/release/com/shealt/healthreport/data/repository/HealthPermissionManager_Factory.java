package com.shealt.healthreport.data.repository;

import com.samsung.android.sdk.health.data.HealthDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class HealthPermissionManager_Factory implements Factory<HealthPermissionManager> {
  private final Provider<HealthDataStore> healthDataStoreProvider;

  public HealthPermissionManager_Factory(Provider<HealthDataStore> healthDataStoreProvider) {
    this.healthDataStoreProvider = healthDataStoreProvider;
  }

  @Override
  public HealthPermissionManager get() {
    return newInstance(healthDataStoreProvider.get());
  }

  public static HealthPermissionManager_Factory create(
      Provider<HealthDataStore> healthDataStoreProvider) {
    return new HealthPermissionManager_Factory(healthDataStoreProvider);
  }

  public static HealthPermissionManager newInstance(HealthDataStore healthDataStore) {
    return new HealthPermissionManager(healthDataStore);
  }
}
