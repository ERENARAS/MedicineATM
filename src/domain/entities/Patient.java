package domain.entities;

import domain.interfaces.User;

/**
 * Patient sınıfı, sistemdeki hasta kullanıcıları temsil eder.
 * Her hasta, bir reçete alabilir ve ATM üzerinden ilaçlarını alabilir.
 * Bu sınıf sadece hastaya ait temel bilgileri içerir.
 *
 */
public class Patient implements User {
    private String name;
    public Patient(String name){
        this.name = name;
    }
    @Override
    public void login() {
        System.out.println("Patient logged the system");
    }
    public String getName() {
        return name;
    }
}
