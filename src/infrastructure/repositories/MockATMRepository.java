//package infrastructure.repositories;
//
//import domain.entities.Medicine;
//import domain.entities.Prescription;
//import domain.interfaces.ATMRepository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//// gecici olarak yapildi daha sonra gercek halini alacak
//public class MockATMRepository implements ATMRepository {
//
//    private Map<String, Integer> stock = new HashMap<>();
//
//
//
//    @Override
//    public float getAmount(Prescription prescription) {
//        return prescription.getMedicines().size() * 20.0f; // her ilaç 20₺
//    }
//
//    @Override
//    public boolean hasStock(Prescription prescription) {
//        return true; // stok hep var
//    }
//
//    @Override
//    public void dispenseMedicine(Prescription prescription) {
//        List<Medicine> meds = prescription.getMedicines();
//        System.out.println("Aşağıdaki ilaçlar verildi:");
//        for (Medicine med : meds) {
//            System.out.println("- " + med.getName());
//        }
//    }
//
//    @Override
//    public void addStock(Medicine medicine, int quantity) {
//        String name = medicine.getName();
//        if (stock.containsKey(name)){
//            int current = stock.get(name);
//            stock.put(name, current+quantity);
//        }
//        else{
//            stock.put(name, quantity);
//        }
//    }
//
//    @Override
//    public boolean removeStock(Medicine medicine, int quantity) {
//        String name = medicine.getName();
//        if (stock.containsKey(name)){
//            int current = stock.get(name);
//            if (current >= quantity) {
//                stock.put(name, current - quantity);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public int getStockMedicine(Medicine medicine) {
//        if (stock.containsKey(medicine.getName())){
//            return stock.get(medicine.getName());
//        }
//        return 0;
//    }
//
//    @Override
//    public List<String> getStockList() {
//        List<String> list = new ArrayList<>(stock.keySet());
//        return list;
//    }
//    public Map<String, Integer> getStock() {
//        return stock;
//    }
//}
