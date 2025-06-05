package domain.factory;

import domain.entities.Doctor;
import domain.entities.Medicine;
import domain.entities.Patient;
import domain.entities.Prescription;
import domain.interfaces.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * PrescriptionFactory, Prescription nesnesi oluşturmak için kullanılan yardımcı sınıftır.
 *
 * Reçete ID'si ve tarihi sistem tarafından otomatik olarak belirlenir.
 * Domain içerisinde reçete üretiminin merkezi noktasıdır.
 */
public class PrescriptionFactory {

    /**
     * Verilen doktor, hasta ve ilaç listesiyle yeni bir Prescription nesnesi oluşturur.
     *
     * @param doctor Reçeteyi yazan doktor
     * @param patient Reçetenin yazıldığı hasta
     * @param medicines Reçeteye yazılan ilaçlar
     * @return Yeni oluşturulmuş Prescription nesnesi
     */
    public static Prescription createPrescription(User doctor, User patient, List<Medicine> medicines){
        if(medicines == null){
            throw new IllegalArgumentException("Reçetede en az bir ilaç olmalıdır.");
        }
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        return new Prescription(id, date, (Doctor) doctor, (Patient) patient, medicines);
    }
}
