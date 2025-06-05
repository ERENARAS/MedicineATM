package domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Prescription sınıfı, bir hastaya bir doktor tarafından yazılan reçeteyi temsil eder.
 *
 * Reçete nesnesi; benzersiz bir kimlik (UUID), yazılma tarihi,
 * doktor bilgisi, hasta bilgisi ve reçetedeki ilaçlardan oluşur.
 *
 * Domain katmanında yer alır ve temel iş verisini taşır.
 */

public class Prescription {
    private UUID id;
    private LocalDate date;
    private Doctor doctor;
    private Patient patient;



    private List<Medicine> medicines;

    /**
     * Yeni bir Prescription nesnesi oluşturur.
     *
     * @param id Reçeteye atanacak benzersiz kimlik
     * @param date Reçetenin yazıldığı tarih
     * @param doctor Reçeteyi yazan doktor
     * @param patient Reçeteyi alan hasta
     * @param medicines Reçeteye yazılan ilaçlar
     */

    public Prescription(UUID id, LocalDate date, Doctor doctor, Patient patient, List<Medicine> medicines) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.medicines = medicines;
    }
    public String getInfo() {
        return "Prescription ID: " + id + ", Doctor: " + doctor.getName() + ", Patient: " + patient.getName();
    }
    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }
}
