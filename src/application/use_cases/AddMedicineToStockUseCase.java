package application.use_cases;

import domain.entities.Medicine;
import domain.interfaces.ATMRepository;

/**
 * Bu Use Case, eczane personelinin ATM'ye yeni ilaç eklemesini sağlar.
 */
public class AddMedicineToStockUseCase {

    private final ATMRepository atmRepository;

    public AddMedicineToStockUseCase(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public void execute(Medicine medicine, int amount) {
        atmRepository.addStock(medicine, amount);
        System.out.println(amount + " adet " + medicine.getName() + " başarıyla eklendi.");
    }
}
