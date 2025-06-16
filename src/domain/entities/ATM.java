package domain.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * ATM sınıfı, makinede bulunan ilaçların stok bilgisini tutar.
 * Şu an sadece tek bir ATM için çalışacak şekilde tasarlanmıştır.
 */
public class ATM {
    private Map<String, Integer> stock;

    public ATM() {
        this.stock = new HashMap<>();
    }

    public Map<String, Integer> getStock() {
        return stock;
    }

    public void setStock(Map<String, Integer> stock) {
        this.stock = stock;
    }

    public int getStockFor(String medicineName) {
        return stock.getOrDefault(medicineName, 0);
    }

    public void increaseStock(String medicineName, int amount) {
        int current = stock.getOrDefault(medicineName, 0);
        stock.put(medicineName, current + amount);
    }

    public boolean removeStock(String medicineName, int amount) {
        int current = stock.getOrDefault(medicineName, 0);
        if (current < amount) return false;
        stock.put(medicineName, current - amount);
        return true;
    }
}
