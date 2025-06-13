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

    public WritePrescriptionUseCase(PrescriptionRepository repository) {
        this.repository = repository;
    }



    /**
     * Reçete oluşturma ve kaydetme işlemini yürütür.
     * öncesinde hastanın alerjisi kontrol edilir yazılmak istenen ilaçlarla
     * eğer yoksa işlem devam eder
     *
     */
    public void execute(Prescription prescription) {
        Patient patient = prescription.getPatient();
        List<Medicine> meds = prescription.getMedicines();
        List<String> allergies = patient.getAllergicMedicines();

        for (int i = 0; i < meds.size(); i++) {
            Medicine med = meds.get(i);
            if (allergies.contains(med.getName())) {
                throw new RuntimeException("⚠ Reçete başarısız: Hasta '" + med.getName() + "' ilacına alerjiktir.");
            }
        }

        repository.save(prescription);
    }
}
