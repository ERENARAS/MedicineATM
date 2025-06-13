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

import java.util.List;
import java.util.Scanner;

public class Main {

    // Ortak repository nesneleri
    static ATMRepository atmRepository = new MockATMRepository();
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
        } else {
            System.out.println("🔒 Bu kullanıcı türü için bir menü bulunmamaktadır.");
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

//
//public static void simulatePharmacyStockActions() {
//        // Domain nesnesi: sadece kullanıcıyı temsil eder
//        PharmacyStaff pharmacy = (PharmacyStaff) new PharmacyFactory().createUser("Eczacı");
//
//        // Repository & UseCase nesneleri oluşturulur
//
//        AddMedicineToStockUseCase addUseCase = new AddMedicineToStockUseCase(atmRepository);
//        ShowAllStockUseCase showUseCase = new ShowAllStockUseCase(atmRepository);
//
//        // İlaçlar
//        Medicine parol = new Medicine("Parol");
//        Medicine aferin = new Medicine("Aferin");
//
//        // Use case'ler doğrudan Main veya controller'dan çalıştırılır
//        addUseCase.execute(parol, 10);
//        addUseCase.execute(aferin, 5);
//
//        // Stok durumu görüntülenir
//        showUseCase.execute();
//
//        // Kullanıcı bilgisi gösterilebilir
//        System.out.println("İşlem yapan: " + pharmacy.getName());
//    }
//    public static void simulateRegister(String name,String email, String password) {
//        TxtUserRepository userRepository = new TxtUserRepository();
//        RegisterUseCase registerUseCase = new RegisterUseCase(userRepository);
//
//        boolean result = registerUseCase.register(name, email, password);
//
//        if (result) {
//            System.out.println("✅ Kayıt başarılı: " + email);
//        } else {
//            System.out.println("❌ Kayıt başarısız: " + email);
//        }
//    }
//
//
//
//    public static void simulateDispense() {
//        Patient patient = (Patient) new PatientFactory().createUser("Ayşe");
//        patient.setAllergicMedicines(List.of("Aferin"));
//        patient.setAmount(100.0f);
//
//        String prescriptionId = "edd785c0-3323-4af2-b4e7-6e027c0eb4d3"; // Gerçek ID'ye göre değiştir
//
//        DispenseMedicineUseCase dispenseUseCase = new DispenseMedicineUseCase(prescriptionRepository, atmRepository);
//        dispenseUseCase.execute(prescriptionId, patient);
//    }
//    public static void simulateLogin(String email, String password) {
//        TxtUserRepository userRepository = new TxtUserRepository();
//        LoginUseCase loginUseCase = new LoginUseCase(userRepository);
//        LogUseCase logger = new LogUseCase();
//        User user = loginUseCase.login(email, password);
//
//        if (user != null) {
//            System.out.println("👤 Giriş yapan kişi: " + user.getName() + " (" + user.getClass().getSimpleName() + ")");
//            logger.log(user); // ✅ Başarılı giriş sonrası log kaydı
//        }
//    }
//}
