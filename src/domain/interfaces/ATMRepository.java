package domain.interfaces;

import domain.entities.Prescription;

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
}
