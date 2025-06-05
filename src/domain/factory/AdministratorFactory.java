package domain.factory;

import domain.entities.SystemAdministrator;
import domain.interfaces.User;

public class AdministratorFactory implements UserFactory{
    @Override
    public User createUser(String name) {
        return new SystemAdministrator(name);
    }
}
