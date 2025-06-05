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
    private final CheckAllergiesUseCase allergyChecker;

    /**
     * Constructor: Use case başlatılırken repository ve alerji kontrolü için gerekli use case enjekte edilir.
     */
    public WritePrescriptionUseCase(PrescriptionRepository repository, CheckAllergiesUseCase allergyChecker) {
        this.repository = repository;
        this.allergyChecker = allergyChecker;
    }


    /**
     * Reçete oluşturma ve kaydetme işlemini yürütür.
     * öncesinde hastanın alerjisi kontrol edilir yazılmak istenen ilaçlarla
     * eğer yoksa işlem devam eder
     *
     * @param doctor Reçeteyi yazan doktor
     * @param patient Reçeteyi alacak hasta
     * @param medicines Reçeteye yazılacak ilaçlar
     */
    public void execute(Doctor doctor, Patient patient, List<Medicine> medicines) {

        for (Medicine med : medicines) {
            if (allergyChecker.hasAllergy(patient, med)) {
                throw new IllegalArgumentException("Hasta " + med.getName() + " ilacına alerjiktir.");
            }
        }

        Prescription prescription = doctor.writePrescription(patient, medicines);

        repository.save(prescription);

        System.out.println("Reçete başarıyla kaydedildi.");
        System.out.println(prescription.getInfo());
    }
}
