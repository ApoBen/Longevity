package com.shealt.healthreport.ui.viewmodels;

import com.shealt.healthreport.data.local.ReportDao;
import com.shealt.healthreport.data.local.SettingsDataStore;
import com.shealt.healthreport.data.repository.HealthPermissionManager;
import com.shealt.healthreport.data.repository.SamsungHealthRepository;
import com.shealt.healthreport.pdf.PdfReportGenerator;
import com.shealt.healthreport.worker.NotificationHelper;
import com.shealt.healthreport.worker.WorkScheduler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<HealthPermissionManager> permissionManagerProvider;

  private final Provider<SamsungHealthRepository> samsungHealthRepositoryProvider;

  private final Provider<PdfReportGenerator> pdfReportGeneratorProvider;

  private final Provider<ReportDao> reportDaoProvider;

  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  private final Provider<WorkScheduler> workSchedulerProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public MainViewModel_Factory(Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<ReportDao> reportDaoProvider, Provider<SettingsDataStore> settingsDataStoreProvider,
      Provider<WorkScheduler> workSchedulerProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
    this.samsungHealthRepositoryProvider = samsungHealthRepositoryProvider;
    this.pdfReportGeneratorProvider = pdfReportGeneratorProvider;
    this.reportDaoProvider = reportDaoProvider;
    this.settingsDataStoreProvider = settingsDataStoreProvider;
    this.workSchedulerProvider = workSchedulerProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(permissionManagerProvider.get(), samsungHealthRepositoryProvider.get(), pdfReportGeneratorProvider.get(), reportDaoProvider.get(), settingsDataStoreProvider.get(), workSchedulerProvider.get(), notificationHelperProvider.get());
  }

  public static MainViewModel_Factory create(
      Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<ReportDao> reportDaoProvider, Provider<SettingsDataStore> settingsDataStoreProvider,
      Provider<WorkScheduler> workSchedulerProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new MainViewModel_Factory(permissionManagerProvider, samsungHealthRepositoryProvider, pdfReportGeneratorProvider, reportDaoProvider, settingsDataStoreProvider, workSchedulerProvider, notificationHelperProvider);
  }

  public static MainViewModel newInstance(HealthPermissionManager permissionManager,
      SamsungHealthRepository samsungHealthRepository, PdfReportGenerator pdfReportGenerator,
      ReportDao reportDao, SettingsDataStore settingsDataStore, WorkScheduler workScheduler,
      NotificationHelper notificationHelper) {
    return new MainViewModel(permissionManager, samsungHealthRepository, pdfReportGenerator, reportDao, settingsDataStore, workScheduler, notificationHelper);
  }
}
