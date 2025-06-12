package application.use_cases;

import domain.interfaces.User;
import domain.interfaces.UserRepository;

public class LoginUseCase {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        if (!userRepository.validateCredentials(email, password)) {
            System.out.println("❌ Giriş başarısız: Şifre veya e-posta yanlış.");
            return null;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("❌ Kullanıcı bulunamadı.");
        } else {
            System.out.println("✅ Giriş başarılı: " + user.getName() + " (" + user.getEmail() + ")");
        }

        return user;
    }
}
