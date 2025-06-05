import application.use_cases.WritePrescriptionUseCase;
import domain.entities.*;
import domain.factory.DoctorFactory;
import domain.factory.UserFactory;
import infrastructure.repositories.TxtPrescriptionRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserFactory doctorFactory = new DoctorFactory();
        Doctor doctor = (Doctor) doctorFactory.createUser("Dr. Eren");


        Patient patient = new Patient("Ayşe");
        List<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine("Parol"));
        meds.add(new Medicine("Aferin"));


        TxtPrescriptionRepository repo = new TxtPrescriptionRepository();
        WritePrescriptionUseCase useCase = new WritePrescriptionUseCase(repo);


        useCase.execute(doctor, patient, meds);
    }
}
