package domain.factory;

import domain.entities.PharmacyStaff;
import domain.interfaces.User;

public class PharmacyFactory implements UserFactory{
    @Override
    public User createUser(String name) {
        return new PharmacyStaff(name);
    }
}
