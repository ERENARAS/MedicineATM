package domain.interfaces;

import domain.entities.Medicine;
import domain.entities.Prescription;

import java.util.List;
import java.util.Map;

/**
 * ATMRepository arayüzü, ATM cihazının ilaç verme süreçlerinde gerçekleştirmesi gereken
 * temel operasyonları tanımlar. Bu arayüz sayesinde sistem, farklı ATM implementasyonlarıyla
 * (örneğin sahte/mock veya gerçek donanım tabanlı) esnek bir şekilde çalışabilir.
 *
 * Clean Architecture prensiplerine uygun olarak bu arayüz, domain katmanında tanımlanır
 * ve üst seviyedeki use case'lerin, alt seviyedeki detaylara (dosya, donanım vb.) bağımlı
 * olmadan çalışmasını sağlar.
 */
public interface ATMRepository {

     /**
      * Verilen reçeteye karşılık gelen ilaçların toplam ücretini hesaplar.
      *
      * @param prescription İlacı verilecek reçete
      * @return İlaçların toplam ücreti (örneğin TL cinsinden)
      */
     float getAmount(Prescription prescription);

     /**
      * ATM cihazında, verilen reçetede yazılı olan tüm ilaçların stokta olup olmadığını kontrol eder.
      *
      * @param prescription Kontrol edilecek reçete
      * @return Tüm ilaçlar mevcutsa true, aksi halde false
      */
     boolean hasStock(Prescription prescription);

     /**
      * Reçetede belirtilen ilaçları hastaya fiziksel olarak teslim eder.
      * Bu işlem sonrasında stok düşebilir ve sistem güncellenebilir.
      *
      * @param prescription Verilecek ilaçların bulunduğu reçete
      */
     void dispenseMedicine(Prescription prescription);

     /**
      * Belirtilen ilacı verilen miktarda stoğa ekler.
      *
      * @param medicine Eklenecek ilaç
      * @param quantity Miktar
      */
     void addStock(Medicine medicine, int quantity);

     /**
      * Belirtilen ilacı stoğundan çıkarır.
      *
      * @param medicine Çıkarılacak ilaç
      * @param quantity Miktar
      * @return true ise başarıyla çıkarıldı, false ise yeterli stok yok
      */
     boolean removeStock(Medicine medicine, int quantity);

     /**
      * Belirli bir ilacın stoktaki miktarını döner.
      *
      * @param medicine Sorgulanacak ilaç
      * @return Stoktaki miktar (0 olabilir)
      */
     int getStockMedicine(Medicine medicine);

     /**
      * Tüm stokları listeler.
      *
      * @return Stoktaki tüm ilaçlar ve miktarları
      */
     List<String> getStockList();

     Map<String, Integer> getStock();
}
