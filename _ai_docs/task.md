# Görevler (TODO List)

## 🏗️ 1. Proje Kurulumu
- [x] `task.md` ve temel gradle dosyalarının oluşturulması
- [x] `local.properties` dosyasına SDK yolunun eklenmesi
- [x] `app/libs` klasörünün oluşturulup `samsung-health-data-api-1.1.0.aar` dosyasının buraya taşınması
- [x] Ana `build.gradle` ve `settings.gradle` konfigürasyonunun yapılması
- [x] Kotlin, Hilt ve Compose bağımlılıklarının eklenmesi
- [x] Projenin başarıyla derlendiğinin (`./gradlew assembleDebug`) doğrulanması

## 📊 2. Veri Katmanı & Modeller (Aşama 2)
- [x] Tüm veri modellerinin (`DailyHealthReport.kt`, `SleepData.kt`, vb.) yazılması
- [x] `SamsungHealthRepository.kt` sınıfının oluşturulması
- [x] `HealthPermissionManager.kt` izin yönetiminin yazılması
- [x] Samsung Health Data SDK bağlantısının kurulması
- [x] Tüm verilerin çekilmesi için sorguların kodlanması

## 📄 3. PDF Rapor Motoru (Aşama 3)
- [x] `PdfReportGenerator.kt` sınıfının oluşturulması
- [x] A4 boyutunda, çok sayfalı PDF çizim taslağının kodlanması
- [x] Canvas API ile verilerin görsel ve tablo olarak çizilmesi
- [x] Rapor dosyasının yerel depolamaya kaydedilmesi

## ⏰ 4. Zamanlayıcı & Arka Plan (Aşama 4)
- [x] `HealthDataWorker.kt` sınıfının kodlanması
- [x] `WorkScheduler.kt` ile günlük zamanlamanın yapılması
- [x] `NotificationHelper.kt` ile PDF hazır bildirimi ve tıklayınca açma desteği

## 🎨 5. Jetpack Compose UI (Aşama 5)
- [x] Modern koyu tema (`Color.kt`, `Theme.kt`, `Type.kt`) kurulumu
- [x] `HomeScreen.kt` (Özet kartları, manuel rapor oluşturma butonu)
- [x] `SettingsScreen.kt` (Saat seçici, otomatik rapor anahtarı, SDK izin durumu)
- [x] `ReportListScreen.kt` (Geçmiş raporları listeleme, açma ve paylaşma)
- [x] `AppNavigation.kt` ile ekranlar arası geçiş

## 💾 6. Veritabanı & Ayarlar (Aşama 6)
- [x] Room veritabanı kurulumu (`AppDatabase.kt`, `ReportDao.kt`, `ReportEntity.kt`)
- [x] Preferences DataStore entegrasyonu (Ayarların kalıcı olması)
- [x] Hilt Dependency Injection modülünün (`AppModule.kt`) yazılması

## 🧪 7. Doğrulama & Test
- [x] `assembleDebug` ile APK derleme testi başarılı
- [ ] Cihaz üzerinde manual testlerin yapılması
- [ ] Rapor oluşturma, açma, paylaşma ve zamanlama kontrollerinin tamamlanması
