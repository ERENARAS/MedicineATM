package domain.interfaces;

import domain.interfaces.User;

public interface UserRepository {
    boolean save(User user);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean validateCredentials(String email, String password);
}
