package com.shealt.healthreport.pdf;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class PdfReportGenerator_Factory implements Factory<PdfReportGenerator> {
  private final Provider<Context> contextProvider;

  public PdfReportGenerator_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PdfReportGenerator get() {
    return newInstance(contextProvider.get());
  }

  public static PdfReportGenerator_Factory create(Provider<Context> contextProvider) {
    return new PdfReportGenerator_Factory(contextProvider);
  }

  public static PdfReportGenerator newInstance(Context context) {
    return new PdfReportGenerator(context);
  }
}
