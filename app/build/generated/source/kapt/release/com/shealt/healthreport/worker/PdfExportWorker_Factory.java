package com.shealt.healthreport.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
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
public final class PdfExportWorker_Factory {
  private final Provider<SamsungHealthRepository> samsungHealthRepositoryProvider;

  private final Provider<HealthPermissionManager> permissionManagerProvider;

  private final Provider<PdfReportGenerator> pdfReportGeneratorProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public PdfExportWorker_Factory(Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.samsungHealthRepositoryProvider = samsungHealthRepositoryProvider;
    this.permissionManagerProvider = permissionManagerProvider;
    this.pdfReportGeneratorProvider = pdfReportGeneratorProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public PdfExportWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, samsungHealthRepositoryProvider.get(), permissionManagerProvider.get(), pdfReportGeneratorProvider.get(), notificationHelperProvider.get());
  }

  public static PdfExportWorker_Factory create(
      Provider<SamsungHealthRepository> samsungHealthRepositoryProvider,
      Provider<HealthPermissionManager> permissionManagerProvider,
      Provider<PdfReportGenerator> pdfReportGeneratorProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new PdfExportWorker_Factory(samsungHealthRepositoryProvider, permissionManagerProvider, pdfReportGeneratorProvider, notificationHelperProvider);
  }

  public static PdfExportWorker newInstance(Context context, WorkerParameters workerParams,
      SamsungHealthRepository samsungHealthRepository, HealthPermissionManager permissionManager,
      PdfReportGenerator pdfReportGenerator, NotificationHelper notificationHelper) {
    return new PdfExportWorker(context, workerParams, samsungHealthRepository, permissionManager, pdfReportGenerator, notificationHelper);
  }
}
