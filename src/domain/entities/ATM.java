package domain.entities;

import java.util.HashMap;
import java.util.Map;

public class ATM {
    private int id;
    private Map<String, Integer> stock;

    public ATM(int id) {
        this.id = id;
        this.stock = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getStock() {
        return stock;
    }

    public void addMedicine(String name, int quantity) {
        stock.put(name, stock.getOrDefault(name, 0) + quantity);
    }

    public boolean removeMedicine(String name, int quantity) {
        int current = stock.getOrDefault(name, 0);
        if (current >= quantity) {
            stock.put(name, current - quantity);
            return true;
        }
        return false;
    }

    public int getStock(String name) {
        return stock.getOrDefault(name, 0);
    }
}
