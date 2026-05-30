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
public final class SamsungHealthRepository_Factory implements Factory<SamsungHealthRepository> {
  private final Provider<HealthDataStore> healthDataStoreProvider;

  public SamsungHealthRepository_Factory(Provider<HealthDataStore> healthDataStoreProvider) {
    this.healthDataStoreProvider = healthDataStoreProvider;
  }

  @Override
  public SamsungHealthRepository get() {
    return newInstance(healthDataStoreProvider.get());
  }

  public static SamsungHealthRepository_Factory create(
      Provider<HealthDataStore> healthDataStoreProvider) {
    return new SamsungHealthRepository_Factory(healthDataStoreProvider);
  }

  public static SamsungHealthRepository newInstance(HealthDataStore healthDataStore) {
    return new SamsungHealthRepository(healthDataStore);
  }
}
