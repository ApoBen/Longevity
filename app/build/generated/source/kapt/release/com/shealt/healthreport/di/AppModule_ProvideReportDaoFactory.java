package com.shealt.healthreport.di;

import com.shealt.healthreport.data.local.AppDatabase;
import com.shealt.healthreport.data.local.ReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class AppModule_ProvideReportDaoFactory implements Factory<ReportDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideReportDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ReportDao get() {
    return provideReportDao(databaseProvider.get());
  }

  public static AppModule_ProvideReportDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideReportDaoFactory(databaseProvider);
  }

  public static ReportDao provideReportDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideReportDao(database));
  }
}
