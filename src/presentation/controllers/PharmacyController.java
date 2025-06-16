package presentation.controllers;

import application.use_cases.AddStockUseCase;
import domain.entities.PharmacyStaff;
import domain.interfaces.ATMRepository;

import java.util.Scanner;

public class PharmacyController {
    private final PharmacyStaff pharmacy;
    private final ATMRepository atmRepository;

    public PharmacyController(PharmacyStaff pharmacy, ATMRepository atmRepository) {
        this.pharmacy = pharmacy;
        this.atmRepository = atmRepository;
    }

    public void openMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n Pharmacy Menu");
            System.out.println("1. ATM Stok Güncelle");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                addStock();
            }
            else if (choice == 0) {
                break;
            }
        }
    }

    private void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("İlaç adı: ");
        String name = scanner.nextLine();
        System.out.print("Eklenecek miktar: ");
        int amount = scanner.nextInt();

        AddStockUseCase useCase = new AddStockUseCase(atmRepository);
        useCase.execute(name, amount);
    }
}
