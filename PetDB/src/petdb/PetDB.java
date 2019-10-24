/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petdb;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author adammaser
 */
public class PetDB {
    static ArrayList<Pet> allPets = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean userChoice = true;
        System.out.println("Pet Database Program.");
        while (userChoice) {
            System.out.print("\nWhat would you like to do?\n"
                + " 1) View all pets\n"
                + " 2) Add more pets\n"
                + " 3) Search by name\n"
                + " 4) Search by age\n"
                + " 5) Exit program\n"
                + "Your choice: ");
            int selection = stdin.nextInt();
            
            switch(selection) {
                case 1: 
                    displayPets();
                    break;
                case 2:
                    addPets();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    searchByAge();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
    
    private static void displayPets() {
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
        }
        System.out.println("+----------------------+");
    }
    
    private static void addPets() {
        int petsAdded = 0;
        System.out.println();
        while (true) {
            Scanner stdin = new Scanner(System.in);
            System.out.print("add pet (name, age): ");
            String response = stdin.nextLine();
            if (response.equals("done")) {
                break;
            } else {
                Scanner parseResponse = new Scanner(response);
                String petName = parseResponse.next();
                int petAge = parseResponse.nextInt();
                Pet newPet = new Pet(petName, petAge);
                allPets.add(newPet);
                petsAdded++;
            }
        }
        System.out.println(petsAdded + " pets added.");
    }
    
    private static void searchByName() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nEnter a name to search: ");
        String nameToSearch = stdin.next();
        
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            if (pet.getName().equals(nameToSearch)) {
                System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
            } 
        }
        System.out.println("+----------------------+");
    }
    
    private static void searchByAge() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nEnter an age to search: ");
        int ageToSearch = stdin.nextInt();
        
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            if (pet.getAge() == ageToSearch) {
                System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
            } 
        }
        System.out.println("+----------------------+");
    }
}
