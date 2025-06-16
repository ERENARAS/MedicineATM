import application.use_cases.LoginUseCase;
import application.use_cases.AddStockUseCase;
import application.use_cases.DispenseMedicineUseCase;
import domain.entities.Patient;
import infrastructure.repositories.TxtUserRepository;
import infrastructure.repositories.TxtATMRepository;
import infrastructure.repositories.TxtPrescriptionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
public class SimpleTest {

    public static void main(String[] args) throws IOException {
        prepareTestFiles();

        TxtUserRepository userRepo = new TxtUserRepository();
        TxtATMRepository atmRepo = new TxtATMRepository();
        TxtPrescriptionRepository presRepo = new TxtPrescriptionRepository();

        List<String> lines = Files.readAllLines(Paths.get("testcases.txt"));
        for (String line : lines) {
            runTest(line, userRepo, atmRepo, presRepo);
        }
    }

    private static void prepareTestFiles() throws IOException {
        Files.copy(Paths.get("test_users.txt"), Paths.get("users.txt"), REPLACE_EXISTING);
        Files.copy(Paths.get("test_atm.txt"), Paths.get("atm.txt"), REPLACE_EXISTING);
        Files.copy(Paths.get("test_prescriptions.txt"), Paths.get("prescriptions.txt"), REPLACE_EXISTING);
    }

    private static void runTest(String line, TxtUserRepository userRepo, TxtATMRepository atmRepo, TxtPrescriptionRepository presRepo) {
        String[] parts = line.split(";");
        String id = parts[0];
        String type = parts[1];

        String p1;
        if (parts.length > 2) {
            p1 = parts[2];
        }
        else {
            p1 = "";
        }

        String p2;
        if (parts.length > 3) {
            p2 = parts[3];
        }
        else {
            p2 = "";
        }

        String exp;
        if (parts.length > 4) {
            exp = parts[4];
        }
        else {
            exp = "";
        }

        boolean pass;
        switch (type) {
            case "LOGIN": {
                LoginUseCase loginUC = new LoginUseCase(userRepo);
                boolean result = loginUC.execute(p1, p2);
                pass = Boolean.toString(result).equals(exp);
                break;
            }
            case "ADD_STOCK": {
                AddStockUseCase addUC = new AddStockUseCase(atmRepo);
                addUC.execute(p1, Integer.parseInt(p2));
                int stock = atmRepo.getStock(p1);
                pass = Integer.toString(stock).equals(exp);
                break;
            }
            case "DISPENSE": {
                DispenseMedicineUseCase dispUC = new DispenseMedicineUseCase(presRepo, atmRepo);
                Patient patient = new Patient("Ulas");
                patient.setEmail(p2);
                dispUC.execute(p1, patient);
                boolean deleted = presRepo.findByID(p1).isEmpty();
                pass = Boolean.toString(deleted).equals(exp);
                break;
            }
            default:
                System.out.println(id + ": UNKNOWN TEST TYPE \"" + type + "\"");
                return;
        }

        if (pass) {
            System.out.println(id + ": PASS");
        }
        else {
            System.out.println(id + ": FAIL");
        }
    }
}
