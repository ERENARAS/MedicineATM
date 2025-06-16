package application.use_cases;

import domain.interfaces.User;
import domain.factory.UserFactory;
import domain.interfaces.UserRepository;

public class RegisterUseCase {
    private final UserRepository userRepository;

    public RegisterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String name, String email, String password) {
        if (!isValidEmail(email)) {
            System.out.println(" Geçersiz e-posta uzantısı.");
            return false;
        }

        if (userRepository.existsByEmail(email)) {
            System.out.println(" Bu e-posta ile kayıtlı kullanıcı zaten var.");
            return false;
        }

        UserFactory factory = getFactoryByEmail(email);
        User user = factory.createUser(name);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@dr.medicine") || email.endsWith("@pt.medicine") || email.endsWith("@ph.medicine");
    }

    private UserFactory getFactoryByEmail(String email) {
        if (email.endsWith("@dr.medicine")){
            return new domain.factory.DoctorFactory();
        }
        if (email.endsWith("@pt.medicine")){
            return new domain.factory.PatientFactory();
        }
        if (email.endsWith("@ph.medicine")) {
            return new domain.factory.PharmacyFactory();
        }
        throw new IllegalArgumentException(" Tanınmayan kullanıcı tipi: " + email);
    }
}
