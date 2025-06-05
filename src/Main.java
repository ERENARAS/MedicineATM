import application.use_cases.CheckAllergiesUseCase;
import application.use_cases.ViewPatientRecordUseCase;
import application.use_cases.WritePrescriptionUseCase;
import domain.entities.*;
import domain.factory.DoctorFactory;
import domain.factory.PatientFactory;
import domain.factory.UserFactory;
import infrastructure.repositories.TxtPrescriptionRepository;

import java.util.ArrayList;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserFactory doctorFactory = new DoctorFactory();
        Doctor doctor = (Doctor) doctorFactory.createUser("Dr. Eren");

        UserFactory patientFactory = new PatientFactory();
        Patient patient = (Patient) patientFactory.createUser("Ayşe");


        List<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine("Parol"));
        meds.add(new Medicine("Aferin"));


        TxtPrescriptionRepository repo = new TxtPrescriptionRepository();
        CheckAllergiesUseCase allergyChecker = new CheckAllergiesUseCase();

        WritePrescriptionUseCase useCase = new WritePrescriptionUseCase(repo, allergyChecker);


        useCase.execute(doctor, patient, meds);

        ViewPatientRecordUseCase useCase2 = new ViewPatientRecordUseCase(repo);
        useCase2.execute(patient); // Doctor ya da Patient tarafından çağrılabilir

    }
}
