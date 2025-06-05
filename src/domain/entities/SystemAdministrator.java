package domain.entities;

import domain.interfaces.User;

public class SystemAdministrator implements User {


    private String name;
    public SystemAdministrator(String name){
        this.name = name;
    }
    @Override
    public void login() {

    }
    public String getName() {
        return name;
    }
}
