package application.use_cases;

import domain.entities.*;
import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;

import java.util.List;
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
            System.out.println("Reçete bulunamadı.");
            return;
        }

        Prescription prescription = optionalPrescription.get();

        String prescription_patient_name = prescription.getPatient().getName();
        String patient_name = patient.getName();
        // Hasta uyuşuyor mu?
        if (!prescription_patient_name.equals(patient_name)) {
            System.out.println("Reçete bu hastaya ait değil.");
            return;
        }

        List<Medicine> medicines = prescription.getMedicines();
        boolean stockAvailable = true;

        // 1. Stok kontrolü
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            int stock = atmRepository.getStockMedicine(med);
            if (stock <= 0) {
                System.out.println("Stokta bulunmayan ilaç: " + med.getName());
                stockAvailable = false;
            }
        }

        if (!stockAvailable) {
            System.out.println("Bazı ilaçlar stokta yok. Dağıtım iptal edildi.");
            return;
        }

        // 2. Para tahsilatı (şu an simülasyon, ileride değiştirilebilir)
        System.out.println("İlaçlar için ödeme alındı.");

        // 3. İlaçları ver → stoğu düşür
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            atmRepository.removeStock(med, 1);
            System.out.println("Verilen ilaç: " + med.getName());
        }

        System.out.println("Tüm ilaçlar başarıyla verildi.");
    }
}
