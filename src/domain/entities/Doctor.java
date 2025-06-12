package domain.entities;
import domain.factory.PrescriptionFactory;
import domain.interfaces.User;
import java.util.List;
/**
 * Doctor sınıfı, sistemdeki doktor kullanıcılarını temsil eder.
 * Doktorlar hastalara reçete yazabilir.
 *
 * Bu sınıf sadece domain düzeyindeki davranışları içerir ve veri erişimi (repository gibi) içermez.
 */

public class Doctor implements User {
    private String name;
    private String Email;
    private String password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String mail) {
        this.Email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Doctor(String name){
        this.name = name;
    }

    /**
     * Doktor, bir hasta için ilaç içeren bir reçete oluşturur.
     * Bu işlem PrescriptionFactory üzerinden gerçekleştirilir.
     *
     * @param patient Reçete yazılacak hasta
     * @param medicines Reçeteye eklenecek ilaçlar
     * @return Oluşturulan Prescription nesnesi
     */
    public Prescription writePrescription(Patient patient, List<Medicine> medicines) {
        return PrescriptionFactory.createPrescription(this, patient, medicines);
    }
    @Override
    public void login() {
        System.out.println("Doctor logged the system");
    }
    public String getName() {
        return name;
    }
}
