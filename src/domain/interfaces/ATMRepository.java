// domain/interfaces/ATMRepository.java
package domain.interfaces;

import domain.entities.ATM;

public interface ATMRepository {
     ATM load();
     void saveATM(ATM atm);
}
