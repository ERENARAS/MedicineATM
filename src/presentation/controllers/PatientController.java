package presentation.controllers;

import application.use_cases.DispenseMedicineUseCase;
import domain.entities.Patient;
import domain.entities.Prescription;
import domain.interfaces.PrescriptionRepository;
import infrastructure.repositories.TxtATMRepository;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PatientController {
    private final Patient patient;
    private final PrescriptionRepository prescriptionRepository;
    private final Scanner scanner = new Scanner(System.in);

    public PatientController(Patient patient, PrescriptionRepository prescriptionRepository) {
        this.patient = patient;
        this.prescriptionRepository = prescriptionRepository;
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Hasta Menüsü ---");
            System.out.println("1- Reçeteleri Görüntüle");
            System.out.println("2- Alerjilerimi Görüntüle");
            System.out.println("3- Alerji Ekle");
            System.out.println("4- Reçeteden İlaç Al");
            System.out.println("5- Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 :
                    viewPrescriptions();
                    break;
                case 2 :
                    viewAllergies();
                    break;
                case 3 :
                    addAllergy();
                    break;
                case 4 :
                    dispenseMedicine();
                    break;
                case 5 :
                    System.out.println(" Görüşmek üzere!");
                    return;

                default :
                    System.out.println(" Geçersiz seçim!");
                    break;
            }
        }
    }

    private void viewPrescriptions() {
        List<Prescription> list = prescriptionRepository.getAll();
        System.out.println("\n Reçeteleriniz:");
        boolean found = false;
        for (Prescription p : list) {
            String mail = patient.getEmail();
            System.out.println(p.getPatient().getEmail());
            if (p.getPatient().getEmail().equals(mail)) {
                System.out.println("ID: " + p.getId());
                System.out.println("İlaçlar: " + p.getMedicines().toString());
                System.out.println("---");
                found = true;
            }
        }
        if (!found) System.out.println(" Reçete bulunamadı.");
    }

    private void viewAllergies() {
        List<String> allergies = patient.getAllergicMedicines();
        if (allergies.isEmpty()) {
            System.out.println(" Kayıtlı alerji yok.");
        } else {
            System.out.println(" Alerjileriniz:");
            for (String allergy : allergies) {
                System.out.println("- " + allergy);
            }
        }
    }

    private void addAllergy() {
        System.out.print("Yeni alerji ekleyin: ");
        String allergy = scanner.nextLine();
        patient.getAllergicMedicines().add(allergy);
        System.out.println(" Alerji eklendi.");
    }

    private void dispenseMedicine() {
        System.out.print("Reçete ID'sini girin: ");
        String id = scanner.nextLine();
        try {
            UUID uuid = UUID.fromString(id);
            DispenseMedicineUseCase useCase = new DispenseMedicineUseCase(prescriptionRepository, new TxtATMRepository());
            useCase.execute(id, patient);
        } catch (Exception e) {
            System.out.println(" Hata: " + e.getMessage());
        }
    }
}
