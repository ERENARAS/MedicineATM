package domain.entities;

import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;
import java.util.Optional;

/**
 * ATM entity’si, benzersiz bir ID’ye sahiptir ve
 * prescriptionRepository ile reçeteyi alır,
 * atmRepository ile stok ve ücret kontrolü yapar,
 * ardından ilaçları verir.
 */
public class ATM {
    private final int id;
    private final ATMRepository atmRepository;
    private final PrescriptionRepository prescriptionRepository;

    /**
     * Constructor injection ile ATM ID’si ve gerekli repository’ler verilir.
     *
     * @param id                   ATM’in kimlik numarası
     * @param atmRepository       Stok ve dağıtım işlemleri için repository
     * @param prescriptionRepository Reçete erişimi için repository
     */
    public ATM(int id, ATMRepository atmRepository, PrescriptionRepository prescriptionRepository) {
        this.id = id;
        this.atmRepository = atmRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * ATM’in kimlik numarasını döner.
     */
    public int getId() {
        return id;
    }

    /**
     * Verilen reçete ID’sine göre ilaç dağıtımı yapar:
     * 1. Reçeteyi prescriptionRepository.findByID ile alır (Optional kullanarak).
     * 2. Stok atmRepository.hasStock ile kontrol edilir.
     * 3. Ücret atmRepository.getAmount ile hesaplanır.
     * 4. atmRepository.dispenseMedicine ile ilaçlar teslim edilir.
     *
     * @param prescriptionId Reçete UUID string’i
     */
    public void dispense(String prescriptionId) {
        Optional<Prescription> optRx = prescriptionRepository.findByID(prescriptionId);
        if (optRx.isEmpty()) {
            System.out.println("Reçete bulunamadı: " + prescriptionId);
            return;
        }

        Prescription prescription = optRx.get();
        if (!atmRepository.hasStock(prescription)) {
            System.out.println("Yeterli stok yok, işlem iptal edildi.");
            return;
        }

        float total = atmRepository.getAmount(prescription);
        System.out.printf("Toplam tutar: %.2f TL%n", total);
        System.out.println("Ödeme alındı. İlaçlar veriliyor...");

        atmRepository.dispenseMedicine(prescription);
        System.out.println("İlaçlar başarıyla teslim edildi.");
    }
}
