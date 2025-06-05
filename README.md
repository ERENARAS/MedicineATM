# 📊 Medicine ATM System

## 📌 Proje Tanımı

**Medicine ATM**, hastaların 7/24 ilaç alabilmesini sağlayan modern, temasız bir ilaç temin sistemidir.
Hastalar, doktorlar tarafından yazılan reçeteleri QR kod veya özel erişim kodu ile Medicine ATM cihazlarına okutarak doğrudan ilaçlarını alabilirler.

Proje, **Clean Architecture** mimarisiyle inşa edilmiştir.
Veri kalıcılığı gerçek veritabanı yerine `.txt` dosyaları ile sağlanmaktadır.

---

## 🧱 Mimaride Kullanılan Katmanlar (Clean Architecture)

```
📆 src
 ├ 📂 domain               → Kurallar ve modeller (Entities, Interfaces, Value Objects)
 ├ 📂 application          → İş akışları ve mantık (Use Cases, Services)
 ├ 📂 infrastructure       → Teknik detaylar (Repository Implementasyonları, Dosya işlemleri)
 ├ 📂 presentation         → Kullanıcı arayüzü / terminal etkileşimi (Controllers)
 ├ 🗋 Main.java            → Uygulamanın giriş noktası
```

---

## 📁 Klasör Açıklamaları

| Klasör                       | Açıklama                                                                                  |
| ---------------------------- | ----------------------------------------------------------------------------------------- |
| `domain/entities`            | `User`, `Doctor`, `Patient`, `Medicine`, `Prescription` gibi ana sınıflar burada bulunur. |
| `domain/interfaces`          | Repository arayüzleri (`UserRepository`, `PrescriptionRepository` vb.) burada yer alır.   |
| `domain/value_objects`       | `Location`, `QRCode`, `AccessCode` gibi değer nesneleri burada tutulur.                   |
| `domain/factory` | `UserFactory`, `DoctorFactory`, `PrescriptionFactory` gibi üretim sınıfları burada yer alır. | 
| `application/use_cases`      | Use Case sınıfları: sistemdeki iş senaryolarını gerçekleştirir.                           |
| `application/services`       | Uygulama düzeyinde destekleyici servisler (`NotificationService`, `AuthService` vb.)      |
| `infrastructure/repositories` | Repository arayüzlerinin `.txt` dosyalarıyla çalışan implementasyonları.                  |
| `infrastructure/services`    | Dosya sistemi, zaman, dış sistem entegrasyonları ("QR okuma, loglama").                   |
| `presentation.controllers`   | CLI ya da kullanıcıya yönelik kontrol sınıfları.                                          |
| `Main.java`                  | Tüm bağımlılıkların bağlandığı ve uygulamanın başlatıldığı yer.                           |

---

## 🔄 Kullanılan Tasarım Desenleri (Design Patterns)

| Pattern            | Kullanım Yeri      | Açıklama                                                                          |
| ------------------ | ------------------ | --------------------------------------------------------------------------------- |
| Factory Pattern    | `UserFactory`      | Kullanıcı rolüne göre `Doctor`, `Patient`, `PharmacyStaff` nesneleri üretilir.    |
| Repository Pattern | Tüm veri erişimi   | Veriye ulaşım interface ile soyutlanır; dosya sistemi bu arayüzü uygular.         |

---

## 🥪 Nasıl Çalıştırılır?

1. Gerekli `.txt` dosyalarını oluştur:

    * `prescriptions.txt`, `users.txt`, `medicines.txt` gibi.
2. `Main.java` dosyasını çalıştır:

   ```bash
   java Main
   ```
3. Terminal üzerinden QR kod girerek simülasyonu test edebilirsin.

---

## 🤠 Tanımlı Use Case'ler

| Use Case                       | Açıklama                                       |
| ------------------------------ | ---------------------------------------------- |
| `DispenseMedicineUseCase`      | Hasta QR ile ilaç alır, stok kontrolü yapılır. |
| `WritePrescriptionUseCase`     | Doktor reçete yazar ve sisteme kaydeder.       |
| `RefillATMUseCase` (opsiyonel) | Eczacı ATM’ye ilaç ekler.                      |
| `AuthenticateUserUseCase`      | Giriş işlemleri için kullanılır.               |

---

## 💡 Geliştirici Notları

* Her yeni **Entity**, **Use Case** veya önemli bir servis eklendiğinde bu `README` belgesi güncellenmelidir.
* Tüm veri erişimi domain’de tanımlı `interface`’ler ile yapılır. Gerçek erişim detayları sadece infrastructure katmanında yer alır.
* `Main` sınıfı uygulama bileşenlerini manuel olarak enjekte eder. (Dependency Injection framework yoksa)

---

## 🛠️ Örnek Geliştirici Komutu

```java
User user = UserFactory.createUser("DOCTOR", "Ali", "ali@example.com", "1234");
DispenseMedicineUseCase useCase = new DispenseMedicineUseCase(prescriptionRepo, medicineRepo, atmRepo);
useCase.execute(user.getQRCode());
```

---

## 👥 Geliştirici Kadrosu

* Alfa Team Members – Clean Architecture, Domain Design

* Eren Aras
* Zahra Alaskarova
* Ulaş Kartal
