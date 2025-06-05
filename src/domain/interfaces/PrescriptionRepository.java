package domain.interfaces;

import domain.entities.Patient;
import domain.entities.Prescription;

import java.util.List;
import java.util.Optional;

/**
 * PrescriptionRepository, reçete verisinin nasıl erişileceğini tanımlayan bir arayüzdür.
 *
 * Bu arayüz, reçeteleri saklayan, geri getiren ve hastaya göre arayan tüm veri kaynakları
 * için bir sözleşme görevi görür. Uygulama, bu arayüz üzerinden çalışır;
 * verinin nerede ve nasıl tutulduğu (örneğin .txt dosyası, veritabanı) bu arayüzün dışında kalır.
 *
 * Clean Architecture'a göre bu yapı, domain katmanında yer alır ve
 * veri erişim detaylarını soyutlar.
 */

public interface PrescriptionRepository {

    /**
     * Verilen reçeteyi kalıcı olarak kaydeder.
     *
     * @param prescription Kaydedilecek reçete
     */
    void save(Prescription prescription);

    /**
     * Sistemde bulunan tüm reçeteleri döner.
     *
     * @return Reçete listesi
     */
    List<Prescription> getAll();

    /**
     * Verilen hastaya ait ilk bulunan reçeteyi döner.
     * optional kullanma nedenimiz patient null donme ihtimali var
     * eger kullanmazsak nullException kontrolu koymamiz gerekiyor
     *
     * @param patient Aranacak hasta
     * @return Reçete varsa Optional ile, yoksa Optional.empty() döner
     */
    Optional<Prescription> findByPatient(Patient patient);
}
