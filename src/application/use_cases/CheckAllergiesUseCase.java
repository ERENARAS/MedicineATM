package application.use_cases;

import domain.entities.Medicine;
import domain.entities.Patient;

import java.util.List;
/**
 * CheckPatientAllergiesUseCase arayüzü, bir ilacın hastanın alerji listesiyle çakışıp çakışmadığını kontrol eder.
 *
 * Bu use case, doktor reçete yazmadan önce veya sistem ilaç verecekken güvenlik için kullanılır.
 */
public class CheckAllergiesUseCase {

    /**
     * Verilen ilacın, hastanın alerji listesinde olup olmadığını kontrol eder.
     *
     * @param patient  Alerji bilgisi kontrol edilecek hasta
     * @param medicine Kontrol edilecek ilaç
     * @return true → hasta bu ilaca alerjiktir, false → güvenli
     */
    public boolean hasAllergy(Patient patient, Medicine medicine) {
        List<String> allergicMeds = patient.getAllergicMedicines();
        return allergicMeds.contains(medicine.getName());
    }
}
