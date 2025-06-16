import java.io.*;
import java.nio.file.*;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleTest {

    public static void main(String[] args) throws IOException {
        Files.copy(Paths.get("test_users.txt"), Paths.get("users.txt"), REPLACE_EXISTING);
        Files.copy(Paths.get("test_atm.txt"), Paths.get("atm.txt"), REPLACE_EXISTING);
        Files.copy(Paths.get("test_prescriptions.txt"), Paths.get("prescriptions.txt"), REPLACE_EXISTING);

        List<String> lines = Files.readAllLines(Paths.get("testcases.txt"));
        for (String line : lines) {
            runTest(line);
        }
    }

    private static void runTest(String line) throws IOException {
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
            case "LOGIN":
                pass = Boolean.toString(login(p1, p2)).equals(exp);
                break;

            case "ADD_STOCK": {
                int stock = addStock(p1, Integer.parseInt(p2));
                if (Integer.parseInt(exp) < stock) {
                    pass = true;
                }
                else {
                    pass = false;
                }
                break;
            }

            case "DISPENSE":
                pass = Boolean.toString(dispense(p1, p2)).equals(exp);
                break;

            default:
                System.out.println(id + ": UNKNOWN TEST TYPE '" + type + "'");
                return;
        }

        if (pass) {
            System.out.println(id + ": PASS");
        }
        else {
            System.out.println(id + ": FAIL");
        }
    }

    // LOGIN: users.txt içinden email,password satırlarını kontrol eder
    private static boolean login(String email, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cols = line.trim().split(",");
                if (cols.length == 2
                        && cols[0].equals(email)
                        && cols[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    // ADD_STOCK: atm.txt dosyasını map'e al, miktarı ekle, tekrar yaz, yeni miktarı döndür
    private static int addStock(String medicine, int qty) throws IOException {
        Map<String, Integer> stock = new LinkedHashMap<>();
        Path path = Paths.get("atm.txt");
        if (Files.exists(path)) {
            for (String line : Files.readAllLines(path)) {
                String[] cols = line.trim().split(",");
                if (cols.length == 2) {
                    stock.put(cols[0], Integer.parseInt(cols[1]));
                }
            }
        }
        int newQty = stock.getOrDefault(medicine, 0) + qty;
        stock.put(medicine, newQty);

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(path))) {
            for (Map.Entry<String, Integer> e : stock.entrySet()) {
                out.println(e.getKey() + "," + e.getValue());
            }
        }
        return newQty;
    }

    // DISPENSE: prescriptions.txt içinden prescriptionId,patientEmail eşleşirse o satırı sil ve true döndür
    private static boolean dispense(String prescId, String patientEmail) throws IOException {
        Path path = Paths.get("prescriptions.txt");
        if (!Files.exists(path)) {
            return false;
        }

        List<String> all = Files.readAllLines(path);
        List<String> kept = new ArrayList<>();
        boolean removed = false;

        for (String line : all) {
            String[] cols = line.trim().split(",");
            if (cols.length >= 2
                    && cols[0].equals(prescId)
                    && cols[1].equals(patientEmail)
                    && !removed) {
                removed = true;
            }
            else {
                kept.add(line);
            }
        }

        if (removed) {
            try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(path))) {
                for (String l : kept) {
                    out.println(l);
                }
            }
        }
        return removed;
    }
}
