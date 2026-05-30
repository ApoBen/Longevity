package com.shealt.healthreport.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.shealt.healthreport.data.local.ReportDao;
import com.shealt.healthreport.data.repository.HealthPermissionManager;
import com.shealt.healthreport.data.repository.SamsungHealthRepository;
import com.shealt.healthreport.pdf.PdfReportGenerator;
import dagger.internal.DaggerGenerated;
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
public final class HealthDataWorker_Factory {
  private final Provider<SamsungHealthRepository> samsungHealthRepositoryProvider;

  private final Provider<HealthPermissionManager> permissionManagerProvider;

  private final Provider<PdfReportGenerator> pdfReportGeneratorProvider;

  private final Provider<ReportDao> reportDaoProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public HealthDataWorker_Factory(Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<ReportDao> reportDaoProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.samsungHealthRepositoryProvider = samsungHealthRepositoryProvider;
    this.permissionManagerProvider = permissionManagerProvider;
    this.pdfReportGeneratorProvider = pdfReportGeneratorProvider;
    this.reportDaoProvider = reportDaoProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public HealthDataWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, samsungHealthRepositoryProvider.get(), permissionManagerProvider.get(), pdfReportGeneratorProvider.get(), reportDaoProvider.get(), notificationHelperProvider.get());
  }

  public static HealthDataWorker_Factory create(
      Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<ReportDao> reportDaoProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new HealthDataWorker_Factory(samsungHealthRepositoryProvider, permissionManagerProvider, pdfReportGeneratorProvider, reportDaoProvider, notificationHelperProvider);
  }

  public static HealthDataWorker newInstance(Context context, WorkerParameters workerParams,
      SamsungHealthRepository samsungHealthRepository, HealthPermissionManager permissionManager,
      PdfReportGenerator pdfReportGenerator, ReportDao reportDao,
      NotificationHelper notificationHelper) {
    return new HealthDataWorker(context, workerParams, samsungHealthRepository, permissionManager, pdfReportGenerator, reportDao, notificationHelper);
  }
}
