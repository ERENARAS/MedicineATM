package domain.entities;

import domain.interfaces.User;

public class PharmacyStaff implements User {
    private String name;
    public PharmacyStaff(String name){
        this.name = name;
    }
    @Override
    public void login() {
        System.out.println("Pharmacy Staff logged the system");
    }
    public String getName() {
        return name;
    }
}
