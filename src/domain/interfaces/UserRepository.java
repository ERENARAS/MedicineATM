package domain.interfaces;
/**
 * UserRepository arayüzü, uygulamadaki kullanıcı User varlıklarının
 * kalıcı depolama ile etkileşimini tanımlar. Kullanıcı ekleme, okuma,
 * varlık kontrolü ve kimlik doğrulama işlemleri bu arayüz üzerinden
 * gerçekleştirilir.
 */
public interface UserRepository {
    boolean save(User user);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean validateCredentials(String email, String password);
}
