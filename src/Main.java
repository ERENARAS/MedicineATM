import application.use_cases.CheckAllergiesUseCase;
import application.use_cases.ViewPatientRecordUseCase;
import application.use_cases.WritePrescriptionUseCase;
import domain.entities.*;
import domain.factory.DoctorFactory;
import domain.factory.PatientFactory;
import domain.factory.UserFactory;
import infrastructure.repositories.TxtPrescriptionRepository;
import application.use_cases.DispenseMedicineUseCase;

import infrastructure.repositories.MockATMRepository;

import java.util.ArrayList;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        simulateDispenseMedicine();

        System.out.println();
        System.out.println("Yapildi dispense");
        System.out.println();

        simulatePrescriptionWriteAndView();
        System.out.println("Write yapildi");
    }

    public static void simulateDispenseMedicine() {
        // Hasta ve doktor oluştur (örnek)
        UserFactory doctorFactory = new DoctorFactory();
        Doctor doctor = (Doctor) doctorFactory.createUser("Dr. Eren");

        PatientFactory patientFactory = new PatientFactory();
        Patient patient = (Patient) patientFactory.createUser("Ayşe");
        patient.setAllergicMedicines(List.of("Aferin"));
        patient.setAmount(100.0f); // Bakiye

        // Repositories
        TxtPrescriptionRepository prescriptionRepository = new TxtPrescriptionRepository();
        MockATMRepository atmRepository = new MockATMRepository();

        // Use case
        DispenseMedicineUseCase useCase = new DispenseMedicineUseCase(prescriptionRepository, atmRepository);

        // Örnek prescriptionId (önceden prescriptions.txt dosyasına kaydedilmiş olmalı)
        String prescriptionId = "ac87c7d3-cf79-4714-adeb-7be8d70569d8"; // örnek

        // Çalıştır
        try {
            useCase.execute(prescriptionId, patient);
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public static void simulatePrescriptionWriteAndView() {
        // Doktor ve hasta oluştur
        Doctor doctor = (Doctor) new DoctorFactory().createUser("Dr. Eren");
        Patient patient = (Patient) new PatientFactory().createUser("Ayşe");

        patient.setAllergicMedicines(List.of("Aferin"));  // alerji bilgisi
        patient.setAmount(100.0f);  // bakiye bilgisi

        // Reçeteye yazılacak ilaçlar
        List<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine("Parol"));
        meds.add(new Medicine("Aferin"));  // alerjik olacak

        // Repos & use cases
        TxtPrescriptionRepository repo = new TxtPrescriptionRepository();
        CheckAllergiesUseCase allergyChecker = new CheckAllergiesUseCase();

        WritePrescriptionUseCase writeUseCase = new WritePrescriptionUseCase(repo, allergyChecker);
        ViewPatientRecordUseCase viewUseCase = new ViewPatientRecordUseCase(repo);

        // 1. Reçete yazma
        try {
            writeUseCase.execute(doctor, patient, meds);
        } catch (Exception e) {
            System.out.println("Reçete yazılamadı: " + e.getMessage());
        }

        // 2. Hasta geçmişini görüntüleme
        viewUseCase.execute(patient);
    }

}
