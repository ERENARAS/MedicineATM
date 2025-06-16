package domain.interfaces;

import domain.entities.ATM;
/**
 * ATMRepository arayüzü, ATM stok verilerinin kalıcı ortamla etkileşimini
 * tanımlar. Uygulamadaki ATM nesnesini yükleme ve kaydetme işlemleri bu
 * arayüz üzerinden gerçekleştirilir.
 */
public interface ATMRepository {
     ATM load();
     void saveATM(ATM atm);
}
