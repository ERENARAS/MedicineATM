// infrastructure/repositories/TxtATMRepository.java
package infrastructure.repositories;

import domain.entities.ATM;
import domain.interfaces.ATMRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TxtATMRepository implements ATMRepository {

    private static final String FILE_PATH = "atm.txt";

    @Override
    public ATM load() {
        ATM atm = new ATM();
        Map<String, Integer> stock = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.strip().split(",");
                if (parts.length == 2) {
                    stock.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("ATM dosyası okunamadı: " + e.getMessage());
        }

        atm.setStock(stock);
        return atm;
    }

    @Override
    public void saveATM(ATM atm) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : atm.getStock().entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("ATM dosyasına yazılamadı: " + e.getMessage());
        }
    }
}
