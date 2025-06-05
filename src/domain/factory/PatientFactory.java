package domain.factory;

import domain.entities.Patient;
import domain.interfaces.User;

public class PatientFactory implements UserFactory {
    @Override
    public User createUser(String name) {
        return new Patient(name);
    }
}
