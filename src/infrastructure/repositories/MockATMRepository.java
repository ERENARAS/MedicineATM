package infrastructure.repositories;

import domain.entities.Medicine;
import domain.entities.Prescription;
import domain.interfaces.ATMRepository;

import java.util.List;

// gecici olarak yapildi daha sonra gercek halini alacak
public class MockATMRepository implements ATMRepository {

    @Override
    public float getAmount(Prescription prescription) {
        return prescription.getMedicines().size() * 20.0f; // her ilaç 20₺
    }

    @Override
    public boolean hasStock(Prescription prescription) {
        return true; // stok hep var
    }

    @Override
    public void dispenseMedicine(Prescription prescription) {
        List<Medicine> meds = prescription.getMedicines();
        System.out.println("Aşağıdaki ilaçlar verildi:");
        for (Medicine med : meds) {
            System.out.println("- " + med.getName());
        }
    }
}
