import application.use_cases.*;
import domain.entities.*;
import domain.factory.*;
import domain.interfaces.ATMRepository;
import domain.interfaces.PrescriptionRepository;
import domain.interfaces.User;
import infrastructure.repositories.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // Ortak repository nesneleri (tek örnek - Singleton olmadan basit çözüm)
    static ATMRepository atmRepository = new MockATMRepository();
    static PrescriptionRepository prescriptionRepository = new TxtPrescriptionRepository();

    public static void main(String[] args) {
//        simulatePharmacyStockActions();         // Eczacı ilaç ekler, stok görüntüler
//        simulatePrescriptionWriteAndView();     // Doktor reçete yazar, hasta görüntüler
//        simulateDispense();                     // Hasta ATM'den ilaç alır
//        simulateRegister("Eren Aras","erenaras@pt.medicine", "1234");
//        simulateRegister("Ayse","aysedursun@dr.medicine", "abcd");
//        simulateRegister("kerem","invalid@gmail.com", "1111"); // geçersiz
//        simulateRegister("Eren a","eren@pt.medicine", "1234"); // tekrar kayıt denemesi
        simulateLogin("eren@pt.medicine", "1234");
        simulateLogin("ayse@dr.medicine", "abcd");
        simulateLogin("hata@pt.medicine", "yanlis");
    }

    public static void simulatePharmacyStockActions() {
        // Domain nesnesi: sadece kullanıcıyı temsil eder
        PharmacyStaff pharmacy = (PharmacyStaff) new PharmacyFactory().createUser("Eczacı");

        // Repository & UseCase nesneleri oluşturulur

        AddMedicineToStockUseCase addUseCase = new AddMedicineToStockUseCase(atmRepository);
        ShowAllStockUseCase showUseCase = new ShowAllStockUseCase(atmRepository);

        // İlaçlar
        Medicine parol = new Medicine("Parol");
        Medicine aferin = new Medicine("Aferin");

        // Use case'ler doğrudan Main veya controller'dan çalıştırılır
        addUseCase.execute(parol, 10);
        addUseCase.execute(aferin, 5);

        // Stok durumu görüntülenir
        showUseCase.execute();

        // Kullanıcı bilgisi gösterilebilir
        System.out.println("İşlem yapan: " + pharmacy.getName());
    }
    public static void simulateRegister(String name,String email, String password) {
        TxtUserRepository userRepository = new TxtUserRepository();
        RegisterUseCase registerUseCase = new RegisterUseCase(userRepository);

        boolean result = registerUseCase.register(name, email, password);

        if (result) {
            System.out.println("✅ Kayıt başarılı: " + email);
        } else {
            System.out.println("❌ Kayıt başarısız: " + email);
        }
    }

    public static void simulatePrescriptionWriteAndView() {
        Doctor doctor = (Doctor) new DoctorFactory().createUser("Dr. Eren");
        Patient patient = (Patient) new PatientFactory().createUser("Ayşe");

        patient.setAllergicMedicines(List.of("Aferin"));  // Alerji bilgisi
        patient.setAmount(100.0f);  // Bakiye bilgisi

        List<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine("Parol"));
        meds.add(new Medicine("Aferin"));  // Alerjik olacak

        CheckAllergiesUseCase allergyChecker = new CheckAllergiesUseCase();
        WritePrescriptionUseCase writeUseCase = new WritePrescriptionUseCase(prescriptionRepository, allergyChecker);
        ViewPatientRecordUseCase viewUseCase = new ViewPatientRecordUseCase(prescriptionRepository);

        try {
            writeUseCase.execute(doctor, patient, meds);
        } catch (Exception e) {
            System.out.println("Reçete yazılamadı: " + e.getMessage());
        }

        viewUseCase.execute(patient);
    }

    public static void simulateDispense() {
        Patient patient = (Patient) new PatientFactory().createUser("Ayşe");
        patient.setAllergicMedicines(List.of("Aferin"));
        patient.setAmount(100.0f);

        String prescriptionId = "edd785c0-3323-4af2-b4e7-6e027c0eb4d3"; // Gerçek ID'ye göre değiştir

        DispenseMedicineUseCase dispenseUseCase = new DispenseMedicineUseCase(prescriptionRepository, atmRepository);
        dispenseUseCase.execute(prescriptionId, patient);
    }
    public static void simulateLogin(String email, String password) {
        TxtUserRepository userRepository = new TxtUserRepository();
        LoginUseCase loginUseCase = new LoginUseCase(userRepository);
        LogUseCase logger = new LogUseCase();
        User user = loginUseCase.login(email, password);

        if (user != null) {
            System.out.println("👤 Giriş yapan kişi: " + user.getName() + " (" + user.getClass().getSimpleName() + ")");
            logger.log(user); // ✅ Başarılı giriş sonrası log kaydı
        }
    }
}
