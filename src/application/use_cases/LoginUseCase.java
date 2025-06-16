package application.use_cases;

import domain.interfaces.User;
import domain.interfaces.UserRepository;

public class LoginUseCase {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Attempts to authenticate a user with the given credentials.
     * @param email User's email
     * @param password User's password
     * @return The authenticated User object, or null if authentication fails
     */
    public User login(String email, String password) {
        if (!userRepository.validateCredentials(email, password)) {
            System.out.println(" Giriş başarısız: Şifre veya e-posta yanlış.");
            return null;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println(" Kullanıcı bulunamadı.");
        } else {
            System.out.println(" Giriş başarılı: " + user.getName() + " (" + user.getEmail() + ")");
        }

        return user;
    }

    /**
     * Convenience method for tests: returns true if login is successful.
     * @param email User's email
     * @param password User's password
     * @return true if login(email, password) returns non-null, false otherwise
     */
    public boolean execute(String email, String password) {
        return login(email, password) != null;
    }
}
