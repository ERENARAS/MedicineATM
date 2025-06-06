package application.use_cases;

import domain.entities.*;
import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;

import java.util.Optional;

/**
 * DispenseMedicineUseCase, bir hastanın ATM üzerinden ilaç almasını sağlar.
 *
 * Hasta, reçete ID’sini veya QR kodunu kullanarak ATM’ye başvurur.
 * Sistem reçeteyi doğrular, toplam tutarı hesaplar, hastanın bakiyesini kontrol eder,
 * ATM’deki stoğu kontrol eder ve ardından ilaçları verir.
 */
public class DispenseMedicineUseCase {

    private final PrescriptionRepository prescriptionRepository;
    private final ATMRepository atmRepository;

    public DispenseMedicineUseCase(PrescriptionRepository prescriptionRepository,
                                   ATMRepository atmRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.atmRepository = atmRepository;
    }

    /**
     * Reçete doğrulanır, ödeme kontrol edilir, stok kontrolü yapılır ve ilaçlar dağıtılır.
     *
     * @param prescriptionId Reçete ID’si
     * @param patient        İlacı alacak hasta
     */
    public void execute(String prescriptionId, Patient patient) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findByID(prescriptionId);

        if (optionalPrescription.isEmpty()) {
            throw new IllegalArgumentException("Reçete bulunamadı: " + prescriptionId);
        }

        Prescription prescription = optionalPrescription.get();

        // Tutar hesaplanır
        float amount = atmRepository.getAmount(prescription);

        if (patient.getAmount() < amount) {
            throw new IllegalStateException("Hasta bakiyesi yetersiz.");
        }

        if (!atmRepository.hasStock(prescription)) {
            throw new IllegalStateException("ATM stoğunda yeterli ilaç yok.");
        }

        // İlaçlar verilir ve bakiye düşürülür
        atmRepository.dispenseMedicine(prescription);
        patient.decreaseAmount(amount);

        System.out.println("İlaçlar başarıyla verildi.");
    }
}
