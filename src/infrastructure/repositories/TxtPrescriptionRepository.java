package infrastructure.repositories;

import domain.entities.*;
import domain.interfaces.PrescriptionRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * TxtPrescriptionRepository sınıfı, PrescriptionRepository arayüzünü uygular
 * ve reçeteleri dosya sistemi üzerinde (prescriptions.txt) saklar.
 *
 * Bu sınıf, reçeteleri yazmak, okumak ve hasta adına göre aramak için kullanılır.
 * Her reçete bir satır olarak saklanır:
 * <UUID>,<Tarih>,<DoctorAdı>,<PatientAdı>,<İlaç1>|<İlaç2>|...
 */
public class TxtPrescriptionRepository implements PrescriptionRepository {

    private final String filePath = "prescriptions.txt";


    /**
     * Verilen reçeteyi dosyaya (append modunda) ekler.
     *
     * @param prescription Kaydedilecek reçete nesnesi
     */
    @Override
    public void save(Prescription prescription) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            // Dosyaya satır olarak yazılacak veri
            StringBuilder line = new StringBuilder();
            line.append(prescription.getId().toString()).append(",");
            line.append(prescription.getDate().toString()).append(",");
            line.append(prescription.getDoctor().getName()).append(",");
            line.append(prescription.getPatient().getName()).append(",");

            List<Medicine> medicines = prescription.getMedicines();
            for (int i = 0; i < medicines.size(); i++) {
                line.append(medicines.get(i).getName());
                if (i < medicines.size() - 1) {
                    line.append("|"); // ilaçları ayırmak için
                }
            }

            writer.write(line.toString());
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            System.out.println("Dosyaya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    /**
     * Dosyadaki tüm reçeteleri okur ve liste olarak döner.
     *
     * @return Reçete listesi
     */

    @Override
    public List<Prescription> getAll() {
        List<Prescription> prescriptions = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return prescriptions; // dosya yoksa boş liste dön
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);

                if (parts.length < 5) continue;

                UUID id = UUID.fromString(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                Doctor doctor = new Doctor(parts[2]);
                Patient patient = new Patient(parts[3]);

                String[] medNames = parts[4].split("\\|");
                List<Medicine> medicines = new ArrayList<>();
                for (String medName : medNames) {
                    medicines.add(new Medicine(medName));
                }

                Prescription prescription = new Prescription(id, date, doctor, patient, medicines);
                prescriptions.add(prescription);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }

        return prescriptions;
    }


    /**
     * Verilen hasta adına ait reçeteleri dosyadan arar ve varsa tamamını döner.
     *
     * Reçeteler hastanın adına göre filtrelenir (isim eşleşmesi, büyük/küçük harfe duyarsız).
     * Eğer reçete bulunmazsa Optional.empty() döner.
     *
     * @param patient Aranan hastanın nesnesi
     * @return Bu hastaya ait reçete listesi varsa Optional.of(liste), yoksa Optional.empty()
     */
    @Override
    public Optional<List<Prescription>> findByPatient(Patient patient) {
        List<Prescription> all = getAll();
        List<Prescription> result = new ArrayList<>();

        for (Prescription p : all) {
            if (p.getPatient().getName().equalsIgnoreCase(patient.getName())) {
                result.add(p);
            }
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

}
