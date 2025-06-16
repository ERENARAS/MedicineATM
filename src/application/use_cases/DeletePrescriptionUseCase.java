package application.use_cases;

import domain.interfaces.PrescriptionRepository;

public class DeletePrescriptionUseCase {
    private final PrescriptionRepository prescriptionRepository;

    public DeletePrescriptionUseCase(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public void execute(String prescriptionId) {
        prescriptionRepository.deleteByID(prescriptionId);
        System.out.println("🗑️ Reçete silindi: " + prescriptionId);
    }
}
