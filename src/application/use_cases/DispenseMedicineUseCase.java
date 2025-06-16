package application.use_cases;

import domain.entities.ATM;
import domain.entities.Medicine;
import domain.entities.Patient;
import domain.entities.Prescription;
import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DispenseMedicineUseCase {

    private final PrescriptionRepository prescriptionRepository;
    private final ATMRepository atmRepository;

    public DispenseMedicineUseCase(PrescriptionRepository prescriptionRepository,
                                   ATMRepository atmRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.atmRepository = atmRepository;
    }

    public void execute(String prescriptionId, Patient patient) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findByID(prescriptionId);
        if (optionalPrescription.isEmpty()) {
            System.out.println("❌ Reçete bulunamadı.");
            return;
        }
        Prescription prescription = optionalPrescription.get();

        if (!prescription.getPatient().getEmail().equals(patient.getEmail())) {
            System.out.println("❌ Reçete bu hastaya ait değil.");
            return;
        }

        ATM atm = atmRepository.load();
        Map<String, Integer> stock = atm.getStock();
        List<Medicine> medicines = prescription.getMedicines();

        // 1. Stok kontrolü
        for (Medicine med : medicines) {
            String name = med.getName();
            int available = stock.getOrDefault(name, 0);
            if (available <= 0) {
                System.out.println("⚠️ Stokta yok: " + name);
                System.out.println("❌ Dağıtım iptal edildi.");
                return;
            }
        }

        // 2. Ödeme işlemi (şu an simülasyon)
        System.out.println("💳 Ödeme başarıyla alındı.");

        // 3. Dağıtım ve stok düşürme
        for (Medicine med : medicines) {
            String name = med.getName();
            stock.put(name, stock.get(name) - 1);
            System.out.println("✅ Verilen ilaç: " + name);
        }

        // 4. Stok kaydet
        atmRepository.saveATM(atm);

        // 5. Reçeteyi sil
        prescriptionRepository.delete(prescriptionId);

        System.out.println("🎉 Tüm ilaçlar başarıyla verildi. Reçete silindi.");
    }
}
