//package infrastructure.repositories;
//
//import domain.entities.ATM;
//import domain.interfaces.ATMRepository;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TxtATMRepository implements ATMRepository {
//    private final String folderPath = "atm_stocks";
//
//    public TxtATMRepository() {
//        File folder = new File(folderPath);
//        if (!folder.exists()) folder.mkdir();
//    }
//
//    @Override
//    public ATM loadATM(int atmId) {
//        File file = new File(folderPath + "/atm_" + atmId + ".txt");
//        ATM atm = new ATM(atmId);
//
//        if (!file.exists()) {
//            System.out.println("ℹ️ ATM dosyası bulunamadı, yeni oluşturulacak: " + file.getName());
//            return atm;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 2) {
//                    String med = parts[0].trim();
//                    int qty = Integer.parseInt(parts[1].trim());
//                    atm.addMedicine(med, qty);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("❌ ATM dosyası okunurken hata oluştu: " + e.getMessage());
//        }
//        return atm;
//    }
//
//    @Override
//    public void saveATM(ATM atm) {
//        File file = new File(folderPath + "/atm_" + atm.getId() + ".txt");
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            for (Map.Entry<String, Integer> entry : atm.getStock().entrySet()) {
//                writer.write(entry.getKey() + "," + entry.getValue());
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("❌ ATM dosyası kaydedilirken hata oluştu: " + e.getMessage());
//        }
//    }
//}
