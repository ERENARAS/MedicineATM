package infrastructure.repositories;

import domain.entities.*;
import domain.interfaces.PrescriptionRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * TxtPrescriptionRepository sınıfı, PrescriptionRepository arayüzünü uygular
 * ve reçeteleri dosya sistemi üzerinde (prescriptions.txt) saklar.
 */
public class TxtPrescriptionRepository implements PrescriptionRepository {

    private String filePath = "prescriptions.txt";

    public TxtPrescriptionRepository() {
        this.filePath = "users.txt";
    }
    public TxtPrescriptionRepository(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void save(Prescription prescription) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            StringBuilder line = new StringBuilder();

            line.append(prescription.getId().toString()).append(",");
            line.append(prescription.getDate().toString()).append(",");
            line.append(prescription.getDoctor().getName()).append(",");
            line.append(prescription.getPatient().getEmail()).append(",");
            line.append(prescription.getPatient().getName()).append(",");

            List<Medicine> medicines = prescription.getMedicines();
            for (int i = 0; i < medicines.size(); i++) {
                line.append(medicines.get(i).getName());
                if (i < medicines.size() - 1) {
                    line.append("|");
                }
            }

            writer.write(line.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Dosyaya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public List<Prescription> getAll() {
        List<Prescription> prescriptions = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) return prescriptions;

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);

                if (parts.length < 6) continue;

                UUID id = UUID.fromString(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                Doctor doctor = new Doctor(parts[2]);

                String patientEmail = parts[3];
                String patientName = parts[4];
                Patient patient = new Patient(patientName);
                patient.setEmail(patientEmail);

                String[] medNames = parts[5].split("\\|");
                List<Medicine> medicines = new ArrayList<>();
                for (String medName : medNames) {
                    medicines.add(new Medicine(medName));
                }

                prescriptions.add(new Prescription(id, date, doctor, patient, medicines));
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }

        return prescriptions;
    }

    @Override
    public Optional<List<Prescription>> findByPatient(Patient patient) {
        List<Prescription> all = getAll();
        List<Prescription> result = new ArrayList<>();

        for (Prescription p : all) {
            if (p.getPatient().getEmail().equalsIgnoreCase(patient.getEmail())) {
                result.add(p);
            }
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
    @Override
    public void deleteByID(String id) {
        List<Prescription> all = getAll();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Prescription p : all) {
                if (!p.getId().toString().equals(id)) {
                    StringBuilder line = new StringBuilder();
                    line.append(p.getId().toString()).append(",");
                    line.append(p.getDate().toString()).append(",");
                    line.append(p.getDoctor().getName()).append(",");
                    line.append(p.getPatient().getName()).append(",");

                    List<Medicine> meds = p.getMedicines();
                    for (int i = 0; i < meds.size(); i++) {
                        line.append(meds.get(i).getName());
                        if (i < meds.size() - 1) {
                            line.append("|");
                        }
                    }

                    writer.write(line.toString());
                    writer.newLine();
                }
            }
        }
        catch (IOException e) {
            System.out.println(" Silme sırasında hata oluştu: " + e.getMessage());
        }
    }
    @Override
    public void delete(String id) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("prescriptions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Reçetenin ID'siyle başlıyorsa ve silinecek ID değilse listeye ekle
                if (!line.trim().isEmpty() && !line.startsWith(id + ",")) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(" Reçete silme hatası: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prescriptions.txt", false))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Reçete dosyası güncellenemedi: " + e.getMessage());
        }
    }



    @Override
    public Optional<Prescription> findByID(String id) {
        List<Prescription> all = getAll();
        for (Prescription p : all) {
            if (p.getId().toString().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
