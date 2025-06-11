package application.use_cases;

import domain.interfaces.ATMRepository;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Bu Use Case, ATM'deki tüm ilaçların ve miktarlarının listesini gösterir.
 */
public class ShowAllStockUseCase {

    private final ATMRepository atmRepository;

    public ShowAllStockUseCase(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public void execute() {
        Map<String, Integer> stock = atmRepository.getStock();

        if (stock.isEmpty()) {
            System.out.println("Stokta hiç ilaç yok.");
            return;
        }

        System.out.println("ATM Stok Durumu:");

        List<String> keys = new ArrayList<>(stock.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Integer value = stock.get(key);
            System.out.println("- " + key + ": " + value + " adet");
        }
    }
}
