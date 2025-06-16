package presentation.controllers;

import application.use_cases.WritePrescriptionUseCase;
import domain.entities.Doctor;
import domain.entities.Medicine;
import domain.entities.Prescription;
import domain.entities.Patient;
import domain.factory.PatientFactory;
import domain.factory.PrescriptionFactory;
import domain.interfaces.User;
import domain.interfaces.UserRepository;
import infrastructure.repositories.TxtPrescriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorController {
    private final Doctor doctor;
    private Patient currentPatient;

    private final UserRepository userRepository;

    public DoctorController(Doctor doctor, UserRepository userRepository) {
        this.doctor = doctor;
        this.userRepository = userRepository;
    }

    public void openMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n Doktor Menüsü:");
            System.out.println("1. Hasta Oluştur / Seç");
            System.out.println("2. Alerji Ekle (hastaya)");
            System.out.println("3. Reçete Yaz");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1:
                    createOrSelectPatient();
                    break;
                case 2:
                    addAllergiesToPatient();
                    break;
                case 3:
                    writePrescription();
                    break;
                case 0:
                    System.out.println(" Çıkış yapılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }

    private void createOrSelectPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Hastanın e-posta adresi: ");
        String patientEmail = scanner.nextLine();

        // Eğer hasta kayıtlıysa, userRepository üzerinden bul
        User user = userRepository.findByEmail(patientEmail);
        if (user instanceof Patient) {
            currentPatient = (Patient) user;
            System.out.println(" Kayıtlı hasta bulundu: " + currentPatient.getName());
            return;
        }

        System.out.println("ℹ Kayıtlı hasta bulunamadı. Yeni hasta oluşturuluyor.");
        System.out.print("Hastanın adı: ");
        String patientName = scanner.nextLine();

        PatientFactory patientFactory = new PatientFactory();
        Patient patient = (Patient) patientFactory.createUser(patientName);
        patient.setEmail(patientEmail);

        boolean saved = userRepository.save(patient);
        if (!saved) {
            System.out.println(" Hasta kaydedilemedi. Belki zaten kayıtlı?");
        }

        currentPatient = patient;
        System.out.println(" Yeni hasta oluşturuldu: " + patient.getName());
    }


    private void addAllergiesToPatient() {
        if (currentPatient == null) {
            System.out.println("⚠ Önce bir hasta seçmelisiniz.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kaç alerjisi var?: ");
        int allergyCount = scanner.nextInt();
        scanner.nextLine();

        List<String> allergies = new ArrayList<>();
        for (int i = 0; i < allergyCount; i++) {
            System.out.print((i + 1) + ". Alerjik olduğu ilaç: ");
            allergies.add(scanner.nextLine());
        }
        currentPatient.setAllergicMedicines(allergies);
        System.out.println(" Alerjiler eklendi.");
    }

    private void writePrescription() {
        if (currentPatient == null) {
            System.out.println(" Önce bir hasta seçmelisiniz.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kaç ilaç yazılacak?: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        List<Medicine> medicines = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.print((i + 1) + ". İlaç adı: ");
            String medName = scanner.nextLine();
            medicines.add(new Medicine(medName));
        }

        PrescriptionFactory factory = new PrescriptionFactory();
        Prescription prescription = factory.createPrescription( doctor, currentPatient, medicines);

        TxtPrescriptionRepository repo = new TxtPrescriptionRepository();
        WritePrescriptionUseCase useCase = new WritePrescriptionUseCase(repo);
        useCase.execute(prescription);

        System.out.println(" Reçete yazıldı ve kaydedildi: " + prescription.getId());
    }
}
