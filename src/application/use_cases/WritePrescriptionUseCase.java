package application.use_cases;

import domain.entities.*;
import domain.interfaces.PrescriptionRepository;

import java.util.List;

/**
 * WritePrescriptionUseCase sınıfı, doktorun bir hastaya reçete yazmasını ve bu reçetenin sisteme kaydedilmesini sağlar.
 *
 * Clean Architecture'ın application (use case) katmanında yer alır.
 * Prescription oluşturma işlemi Doctor sınıfı tarafından yapılır, ardından repository aracılığıyla saklanır.
 */
public class WritePrescriptionUseCase {

    private final PrescriptionRepository repository;

    /**
     * Use case başlatılırken ihtiyaç duyduğu repository dışarıdan verilir.
     *
     * @param repository Reçetelerin kaydedileceği repository
     */
    public WritePrescriptionUseCase(PrescriptionRepository repository) {
        this.repository = repository;
    }


    /**
     * Reçete oluşturma ve kaydetme işlemini yürütür.
     *
     * @param doctor Reçeteyi yazan doktor
     * @param patient Reçeteyi alacak hasta
     * @param medicines Reçeteye yazılacak ilaçlar
     */
    public void execute(Doctor doctor, Patient patient, List<Medicine> medicines) {
        // 1. Doctor reçeteyi oluşturur
        Prescription prescription = doctor.writePrescription(patient, medicines);

        // 2. Reçete kaydedilir
        repository.save(prescription);

        // 3. Geri bildirim verilir
        System.out.println("Reçete başarıyla kaydedildi.");
        System.out.println(prescription.getInfo());
    }
}
