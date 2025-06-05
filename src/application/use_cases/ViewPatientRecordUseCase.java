package application.use_cases;

import domain.entities.Patient;
import domain.entities.Prescription;
import domain.interfaces.PrescriptionRepository;

import java.util.List;
import java.util.Optional;

/**
 * ViewPatientRecordUseCase sınıfı, bir hastanın alerji bilgilerini ve reçete geçmişini görüntülemek için kullanılır.
 *
 * Clean Architecture kapsamında application katmanında yer alır.
 * Doktorlar ya da hastalar, bu use case aracılığıyla hasta kayıtlarını görüntüleyebilir.
 *
 * Kullanımı:
 * - Hasta nesnesi parametre olarak verilir.
 * - Sistem, ilgili hastaya ait reçete geçmişini PrescriptionRepository üzerinden alır.
 * - Sonuç konsola yazdırılır.
 */
public class ViewPatientRecordUseCase {

    private final PrescriptionRepository prescriptionRepository;

    /**
     * Use case oluşturulurken reçete verilerine erişim sağlayan repository enjekte edilir.
     *
     * @param prescriptionRepository Reçeteleri sağlayan repository
     */
    public ViewPatientRecordUseCase(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Belirtilen hastanın alerji bilgilerini ve geçmiş reçetelerini konsola yazdırır.
     *
     * @param patient Görüntülenecek hasta
     */
    public void execute(Patient patient) {
        System.out.println("Hasta Adı: " + patient.getName());
        System.out.println("Alerjileri: " + patient.getAllergicMedicines());


        Optional<List<Prescription>> optionalPrescriptions = prescriptionRepository.findByPatient(patient);

        if (optionalPrescriptions.isEmpty() || optionalPrescriptions.get().isEmpty()) {
            System.out.println("Bu hastaya ait reçete kaydı bulunamadı.");
        } else {
            System.out.println("Reçete Geçmişi:");
            for (Prescription p : optionalPrescriptions.get()) {
                System.out.println(p.getInfo());
            }
        }
    }
}
