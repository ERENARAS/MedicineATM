import application.use_cases.*;
import presentation.controllers.DoctorController;
import presentation.controllers.PatientController;
import domain.entities.*;
import domain.factory.*;
import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;
import domain.interfaces.User;
import domain.interfaces.UserRepository;
import infrastructure.repositories.*;
import presentation.controllers.PharmacyController;

import java.util.List;
import java.util.Scanner;

public class Main {

    // Ortak repository nesneleri
    static ATMRepository atmRepository = new TxtATMRepository();
    static PrescriptionRepository prescriptionRepository = new TxtPrescriptionRepository();
    static UserRepository userRepository = new TxtUserRepository();

    public static void main(String[] args) {
        User user = authenticateUserFlow();
        if (user == null) return;

        if (user instanceof Doctor) {
            DoctorController controller = new DoctorController((Doctor) user, userRepository);
            controller.openMenu();
        } else if (user instanceof Patient) {
            PatientController controller = new PatientController((Patient) user, prescriptionRepository);
            controller.run();
        }
        else if (user instanceof PharmacyStaff) {
            PharmacyController controller = new PharmacyController((PharmacyStaff) user, atmRepository);
            controller.openMenu();
        }
        else {
            System.out.println(" Bu kullanıcı türü için bir menü bulunmamaktadır.");
        }
    }

    private static User authenticateUserFlow() {
        Scanner scanner = new Scanner(System.in);
        RegisterUseCase registerUseCase = new RegisterUseCase(userRepository);
        LoginUseCase loginUseCase = new LoginUseCase(userRepository);
        LogUseCase logLoginUseCase = new LogUseCase();

        System.out.print("1- Giriş\n2- Kayıt\nSeçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("E-posta: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        if (choice == 2) {
            System.out.print("Adınız: ");
            String name = scanner.nextLine();
            boolean success = registerUseCase.register(name, email, password);
            if (!success) return null;
        }

        User user = loginUseCase.login(email, password);
        if (user != null) {
            logLoginUseCase.log(user);
        }
        return user;
    }
}
