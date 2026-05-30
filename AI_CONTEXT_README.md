# 🤖 AI AJAN İÇİN BAĞLAM DOSYASI — ÖNCELİKLE BUNU OKU

## Proje Nedir?
Samsung Health uygulamasından sağlık verilerini (uyku, adım, nabız, enerji skoru, egzersiz vb.) çekip günlük PDF rapor oluşturan bir **Android uygulaması**.

## Mevcut Durum: ✅ Derleniyor, ⚠️ İzin Sorunu Var

- `./gradlew assembleDebug` **BAŞARILI** — APK oluşuyor
- APK dosyası: `app/build/outputs/apk/debug/app-debug.apk`
- **AKTİF SORUN**: "İzin Ver" butonuna basıldığında Samsung Health izin diyaloğu açılmıyor. Exception sessizce yakalanıyor olabilir.

## Derleme Komutu
```bash
JAVA_HOME=/home/steppenwolf/.local/lib/jdk21 ./gradlew assembleDebug
```

## Samsung Health SDK Bilgileri
- SDK: `samsung-health-data-api-1.1.0.aar` (app/libs/ içinde)
- SDK JAR incelemesi için: `/tmp/shealt_aar_inspect/classes.jar` (AAR'dan çıkarılmış)
- Samsung Health'te **Developer Mode for Data Read: ON** yapılmış durumda
- Play Store'a yüklenmeyecek, sadece kişisel kullanım

## Kritik Dosyalar ve Yapıları

### Proje Yapısı
```
shealt/
├── app/
│   ├── libs/samsung-health-data-api-1.1.0.aar  ← Samsung Health SDK
│   ├── build.gradle                              ← Bağımlılıklar
│   └── src/main/
│       ├── AndroidManifest.xml                   ← İzinler, FileProvider, WorkManager
│       ├── res/xml/file_paths.xml                ← PDF paylaşımı için
│       └── java/com/shealt/healthreport/
│           ├── HealthReportApp.kt                ← Application (Hilt + WorkManager)
│           ├── MainActivity.kt                   ← Compose entry point
│           ├── data/
│           │   ├── model/DailyHealthReport.kt    ← 16 veri modeli (tek dosyada)
│           │   ├── repository/
│           │   │   ├── SamsungHealthRepository.kt ← SDK veri çekme
│           │   │   └── HealthPermissionManager.kt ← İzin yönetimi ⚠️ SORUNLU
│           │   └── local/
│           │       ├── AppDatabase.kt            ← Room DB
│           │       ├── ReportEntity.kt           ← Rapor tablosu
│           │       ├── ReportDao.kt              ← DB sorguları
│           │       └── SettingsDataStore.kt      ← Kullanıcı ayarları
│           ├── pdf/PdfReportGenerator.kt         ← A4 PDF oluşturma (Canvas API)
│           ├── worker/
│           │   ├── HealthDataWorker.kt           ← Arka plan rapor oluşturma
│           │   ├── WorkScheduler.kt              ← Günlük zamanlama
│           │   └── NotificationHelper.kt         ← Bildirim sistemi
│           ├── di/AppModule.kt                   ← Hilt DI modülü
│           └── ui/
│               ├── theme/Theme.kt                ← Material 3 koyu tema
│               ├── viewmodels/MainViewModel.kt   ← Ana ViewModel
│               ├── screens/
│               │   ├── HomeScreen.kt             ← Ana sayfa (izin + rapor butonu)
│               │   ├── SettingsScreen.kt         ← Ayarlar (saat seçici)
│               │   └── ReportListScreen.kt       ← Geçmiş raporlar
│               └── navigation/AppNavigation.kt   ← Bottom nav (3 ekran)
├── build.gradle                                  ← Project-level
├── settings.gradle
├── gradle.properties
└── local.properties                              ← SDK yolu
```

### Teknoloji Stack
- Kotlin + Jetpack Compose (Material 3)
- Hilt (Dependency Injection)
- Room (Database)
- DataStore (Preferences)
- WorkManager (Arka plan zamanlama)
- Samsung Health Data SDK 1.1.0
- Android PDF Document API (Canvas)

### Bağımlılık Sürümleri (app/build.gradle)
- compileSdk: 34, minSdk: 29, targetSdk: 34
- Kotlin: 2.0.0 (compose plugin)
- Compose BOM: 2024.05.00
- Hilt: 2.51.1
- Room: 2.6.1
- WorkManager: 2.9.0
- Java: 21

## ⚠️ AKTİF SORUN: İzin Diyaloğu Açılmıyor

### Sorunun Tanımı
Kullanıcı "İzin Ver" butonuna basıyor ama Samsung Health izin diyaloğu açılmıyor.

### Son Yapılan Değişiklikler
1. `HealthPermissionManager.kt` — Exception artık re-throw ediliyor (sessizce yutulmuyor)
2. `MainViewModel.kt` — `errorMessage` ve `statusMessage` state flow'ları eklendi
3. `HomeScreen.kt` — Hata mesajları Snackbar ve Card ile gösteriliyor

### Olası Nedenler (Araştırılması Gereken)
1. `HealthDataService.getStore(context)` bağlantısı düzgün kurulmamış olabilir
2. Samsung Health SDK'nın `requestPermissions()` metodu Activity lifecycle ile uyumsuz olabilir
3. İzin istenen DataType'lar SDK sürümüyle uyumsuz olabilir
4. `STEPS_GOAL` ve `ENERGY_SCORE` izin isteme'den kaldırıldı ama repository'de hala kullanılıyor

### SDK API Referansı (javap ile çıkarılmış)
```
HealthDataStore interface:
  - requestPermissions(Set<Permission>, Activity) -> Set<Permission>  (suspend)
  - getGrantedPermissions(Set<Permission>) -> Set<Permission>  (suspend)
  - readData(ReadDataRequest) -> DataResponse
  - aggregateData(AggregateRequest) -> DataResponse

HealthDataService:
  - getStore(Context) -> HealthDataStore  (static)
  - getStore(Context, CoroutineScope) -> HealthDataStore

DataTypes (mevcut veri tipleri):
  SLEEP, STEPS, HEART_RATE, EXERCISE, EXERCISE_LOCATION,
  SKIN_TEMPERATURE, BLOOD_OXYGEN, ACTIVITY_SUMMARY, FLOORS_CLIMBED,
  BLOOD_GLUCOSE, BLOOD_PRESSURE, BODY_COMPOSITION, SLEEP_GOAL,
  STEPS_GOAL, ACTIVE_CALORIES_BURNED_GOAL, ACTIVE_TIME_GOAL,
  WATER_INTAKE, WATER_INTAKE_GOAL, NUTRITION, NUTRITION_GOAL,
  ENERGY_SCORE, USER_PROFILE, SLEEP_APNEA,
  IRREGULAR_HEART_RHYTHM_NOTIFICATION, BODY_TEMPERATURE

SleepType fields: DURATION(Duration), SESSIONS(List<SleepSession>), SLEEP_SCORE(Int)
  - Builder: DualTimeBuilder (setLocalTimeFilter veya setInstantTimeFilter)
  - Aggregate: TOTAL_DURATION

StepsType fields: TOTAL(Long)
  - Sadece aggregate desteği var, read yok
  - Aggregate builder: LocalTimeBuilder

HeartRateType fields: HEART_RATE(Float)
  - Builder: DualTimeBuilder

EnergyScoreType fields: ENERGY_SCORE(Float)
  - Builder: LocalDateBuilder (setLocalDateFilter)

ExerciseType fields: SESSIONS(List<ExerciseSession>)
  - Builder: DualTimeBuilder

HealthDataPoint methods:
  - getValue(Field<T>) -> T
  - getValueOrDefault(Field<T>, T) -> T
  - getStartTime() -> Instant
  - getEndTime() -> Instant
  - getStartLocalDateTime() -> LocalDateTime
  - getEndLocalDateTime() -> LocalDateTime
```

### SamsungHealthRepository Sorunları
Repository'de `read` helper extension kullanılıyor:
```kotlin
import com.samsung.android.sdk.health.data.helper.read
import com.samsung.android.sdk.health.data.helper.aggregate
```
Bu helper'lar `RequestHelperKt` sınıfından geliyor ve lambda-style builder kabul ediyor.
Bazı DataType'lar `DualTimeBuilder` (localtime/instant), bazıları `LocalDateBuilder` kullanıyor.
Builder tipi uyumsuzluğu runtime'da exception fırlatabilir.

## Kullanıcı Hakkında
- Dil: Türkçe
- Samsung telefon kullanıyor
- Samsung Health'te Developer Mode açık (Data Read: ON)
- Play Store'a yüklemeyecek, sadece kendi kullanımı için
- APK'yı doğrudan cihaza yüklüyor (sideload)

## Planlama Belgeleri
- `_ai_docs/implementation_plan.md` — Kapsamlı uygulama planı (tüm veri tipleri, mimari, UI tasarım)
- `_ai_docs/task.md` — Görev takip listesi
- `_ai_docs/walkthrough.md` — Proje özeti ve yapılanlar
