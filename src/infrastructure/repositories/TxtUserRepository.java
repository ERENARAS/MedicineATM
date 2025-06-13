package infrastructure.repositories;
import domain.factory.UserFactory;
import domain.factory.DoctorFactory;
import domain.factory.PatientFactory;
import domain.factory.PharmacyFactory;

import domain.interfaces.User;
import domain.interfaces.UserRepository;

import java.io.*;

public class TxtUserRepository implements UserRepository {
    private final String filePath = "users.txt";

    @Override
    public boolean save(User user) {
        if (existsByEmail(user.getEmail())) return false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getEmail() + "," + user.getName() + "," + user.getPassword());
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private UserFactory getFactoryByEmail(String email) {
        if (email.endsWith("@dr.medicine")) {
            return new DoctorFactory();
        } else if (email.endsWith("@pt.medicine")) {
            return new PatientFactory();
        } else if (email.endsWith("@ph.medicine")) {
            return new PharmacyFactory();
        } else {
            throw new IllegalArgumentException("Unknown user type: " + email);
        }
    }

    public User findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(email)) {
                    String name = parts[1];
                    String password = parts[2];

                    UserFactory factory = getFactoryByEmail(email);
                    User user = factory.createUser(name);
                    user.setEmail(email);  // Sırasını koru
                    user.setPassword(password);

                    // Debug için ekle
                    System.out.println("✅ User bulundu ve set edildi: " + user.getEmail() + ", " + user.getName());
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Kullanıcı bulunurken hata oluştu: " + e.getMessage());
        }
        return null;
    }



    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public boolean validateCredentials(String email, String password) {
        User user = findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
