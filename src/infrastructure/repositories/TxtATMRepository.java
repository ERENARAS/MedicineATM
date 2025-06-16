package infrastructure.repositories;

import domain.entities.ATM;
import domain.interfaces.ATMRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * TxtATMRepository sınıfı, ATM stok verilerini TXT formatında saklamak ve
 * geri yüklemek için kullanılan bir infrastructure repository implementasyonudur.
 */

public class TxtATMRepository implements ATMRepository {

    private static String FILE_PATH = "atm.txt";

    public TxtATMRepository() {
        this.FILE_PATH = "atm.txt";
    }
    public TxtATMRepository(String filePath){
        this.FILE_PATH = filePath;
    }

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
        }
        catch (IOException e) {
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
        }
        catch (IOException e) {
            System.out.println("ATM dosyasına yazılamadı: " + e.getMessage());
        }
    }
    /**
     * Test/sınıf içi hızlı erişim için stok bilgisini getirir.
     * @param medicine İlacın adı
     * @return Mevcut stok miktarı (ilac bulunamazsa 0)
     */
    public int getStock(String medicine) {
        ATM atm = load();
        return atm.getStock().getOrDefault(medicine, 0);
    }
}
