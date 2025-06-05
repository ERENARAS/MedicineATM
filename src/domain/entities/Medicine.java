package domain.entities;

import java.util.UUID;

/**
 * Medicine sınıfı, sistemdeki ilaçları temsil eder.
 * Her Medicine nesnesi, reçetede yer alabilir ve ATM üzerinden hastaya verilebilir.
 */
public class Medicine {


    private final String  name;

    private final UUID id;
    public Medicine(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }
    public String getName() {
        return name;
    }
    public UUID getId() {
        return id;
    }
}
