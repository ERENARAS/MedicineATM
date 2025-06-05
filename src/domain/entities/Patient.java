package domain.entities;

import domain.interfaces.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient sınıfı, sistemdeki hasta kullanıcıları temsil eder.
 * Her hasta, bir reçete alabilir ve ATM üzerinden ilaçlarını alabilir.
 * Bu sınıf sadece hastaya ait temel bilgileri içerir.
 *
 */
public class Patient implements User {
    private String name;

    private List<String> allergicMedicines;


    public Patient(String name) {
        this.name = name;
        this.allergicMedicines = new ArrayList<>();
    }
    @Override
    public void login() {
        System.out.println("Patient logged the system");
    }
    public String getName() {
        return name;
    }
    public List<String> getAllergicMedicines() {
        return allergicMedicines;
    }
    public void setAllergicMedicines(List<String> allergicMedicines) {
        this.allergicMedicines = allergicMedicines;
    }

}
