package application.use_cases;

import domain.entities.Medicine;
import domain.interfaces.ATMRepository;

/**
 * Bu Use Case, ATM'deki belirli bir ilacın stoktan çıkarılmasını sağlar.
 */
public class RemoveMedicineFromStockUseCase {

    private final ATMRepository atmRepository;

    public RemoveMedicineFromStockUseCase(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public void execute(Medicine medicine, int amount) {
        boolean success = atmRepository.removeStock(medicine, amount);
        if (success) {
            System.out.println(amount + " adet " + medicine.getName() + " stoktan çıkarıldı.");
        } else {
            System.out.println("Stoktan çıkarma başarısız: yeterli miktar yok.");
        }
    }
}
