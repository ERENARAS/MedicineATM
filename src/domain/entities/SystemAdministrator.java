package domain.entities;

import domain.interfaces.User;

public class SystemAdministrator implements User {


    private String name;
    private String Email;
    private String password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String mail) {
        this.Email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
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
