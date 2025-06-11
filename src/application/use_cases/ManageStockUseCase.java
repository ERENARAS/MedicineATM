package application.use_cases;

import domain.entities.Medicine;
import domain.interfaces.ATMRepository;

import java.util.List;

public class ManageStockUseCase {

    private final ATMRepository atmRepository;

    public ManageStockUseCase(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public void addMedicine(Medicine medicine, int quantity) {
        atmRepository.addStock(medicine, quantity);
        System.out.println(quantity + " adet '" + medicine.getName() + "' stoğa eklendi.");
    }

    public void removeMedicine(Medicine medicine, int quantity) {
        boolean success = atmRepository.removeStock(medicine, quantity);
        if (success) {
            System.out.println(quantity + " adet '" + medicine.getName() + "' stoktan çıkarıldı.");
        } else {
            System.out.println("Yetersiz stok: '" + medicine.getName() + "'");
        }
    }

    public void showAllStock() {
        List<String> stockList = atmRepository.getStockList();
        if (stockList.isEmpty()) {
            System.out.println("Stokta hiç ilaç yok.");
        } else {
            System.out.println("Stoktaki İlaçlar:");
            for (int i = 0; i < stockList.size(); i++) {
                System.out.println("- " + stockList.get(i));
            }
        }
    }

}
