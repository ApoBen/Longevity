package com.shealt.healthreport.di;

import android.content.Context;
import com.samsung.android.sdk.health.data.HealthDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideHealthDataStoreFactory implements Factory<HealthDataStore> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideHealthDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HealthDataStore get() {
    return provideHealthDataStore(contextProvider.get());
  }

  public static AppModule_ProvideHealthDataStoreFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideHealthDataStoreFactory(contextProvider);
  }

  public static HealthDataStore provideHealthDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHealthDataStore(context));
  }
}
