/*
 * CSC 422
 * Adam Maser
 * 10/27/2019
 * assignment 1: version control practice with pet program
 */
package petdb;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author adammaser
 */
public class PetDB implements Serializable {
    static ArrayList<Pet> allPets = new ArrayList<>();
    
    public static void main(String[] args) {
        //load ArrayList from file, if it exists
        File petFile = new File("pets");
        if (petFile.exists()) {
            try {
                //get file and create input streams
                FileInputStream fileIn = new FileInputStream(petFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                //read objects into Pet ArrayList
                allPets = (ArrayList<Pet>) objectIn.readObject();
                //close streams
                objectIn.close();
                fileIn.close();
            } catch(Exception ex) {
                //alert user if there was an error loading pets
                System.out.println("Error loading pets from file.");
            }
            
        }
        Scanner stdin = new Scanner(System.in);
        System.out.println("Pet Database Program.");
        while (true) {
            System.out.print("\nWhat would you like to do?\n"
                + " 1) View all pets\n"
                + " 2) Add more pets\n"
                + " 3) Update an existing pet\n"
                + " 4) Remove an existing pet\n"
                + " 5) Search by name\n"
                + " 6) Search by age\n"
                + " 7) Exit program\n"
                + "Your choice: ");
            int selection = stdin.nextInt();
            
            //program choice control
            switch(selection) {
                case 1: 
                    displayPets();
                    break;
                case 2:
                    addPets();
                    break;
                case 3:
                    updatePet();
                    break;
                case 4:
                    removePet();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    searchByAge();
                    break;
                case 7:
                    saveFile();
                    System.exit(0);
            }
        }
    }
    /*
    
        Methods to handle each choice
    
    */
    private static void displayPets() {
        //print header
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        //loop through all pets and add to table
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
        }
        System.out.println("+----------------------+");//print footer
    }
    
    private static void addPets() {
        int petsAdded = 0; //stores amount of pets added
        System.out.println();
        while (true) {
            Scanner stdin = new Scanner(System.in);
            System.out.print("add pet (name, age): ");
            String response = stdin.nextLine();
            //if user types 'done', addPets exits
            if (response.equals("done")) {
                break;
            } else {
                //if db has 5 entries, don't allow anymore pets
                if (allPets.size() == 5) {
                    System.out.println("Error: Database is full.");
                    break;
                }
                Scanner parseResponse = new Scanner(response);
                
                String petName = parseResponse.next();
                int petAge = parseResponse.nextInt();
                //test age, if outside of range 1-20, reset loop
                if (petAge < 1 || petAge > 20) {
                    System.out.println("Error: " + petAge + " is not a valid age.");
                    continue;
                }
                Pet newPet = new Pet(petName, petAge);
                allPets.add(newPet);
                
                petsAdded++;
            }
        }
        System.out.println(petsAdded + " pets added.");
    }
    
    private static void updatePet() {
        displayPets(); //displays all pets before prompting user
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the pet ID you want to update: ");
        int petToUpdate = stdin.nextInt();
        //get pet
        Pet pet = allPets.get(petToUpdate);
        System.out.print("Enter new name and new age: ");
        String newName = stdin.next();
        int newAge = stdin.nextInt();
        //update pet
        pet.setName(newName);
        pet.setAge(newAge);
    }
    
    private static void removePet() {
        displayPets(); //display all pets before prompting user
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the pet ID you want to remove: ");
        int petToDelete = stdin.nextInt();
        //get pet
        Pet pet = allPets.get(petToDelete);
        System.out.println(pet.getName() + " " + pet.getAge() + " has been removed.");
        //delete pet
        allPets.remove(petToDelete);
    }
    
    private static void searchByName() {
        //prompt user
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nEnter a name to search: ");
        String nameToSearch = stdin.next();
        //print table header
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        //loop through each pet, adding to table if it matches name
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            if (pet.getName().equals(nameToSearch)) {
                System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
            } 
        }
        System.out.println("+----------------------+");//print footer
    }
    
    private static void searchByAge() {
        //prompt user
        Scanner stdin = new Scanner(System.in);
        System.out.print("\nEnter an age to search: ");
        int ageToSearch = stdin.nextInt();
        //print header
        System.out.print("\n+----------------------+\n");
        System.out.printf("|%3s | %-10s|%4s |", "ID", "NAME", "AGE");
        System.out.print("\n+----------------------+\n");
        //loop through each pet, adding to table if it matches age
        for (int i = 0; i< allPets.size(); i++) {
            Pet pet = allPets.get(i);
            if (pet.getAge() == ageToSearch) {
                System.out.printf("|%3d | %-10s|%4d |\n", i, pet.getName(), pet.getAge());
            } 
        }
        System.out.println("+----------------------+");//print footer
    }
    
    private static void saveFile() {
        try {
            //create new file and output stream
            FileOutputStream fileOut = new FileOutputStream("pets");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            //write ArrayList of pets to file
            objectOut.writeObject(allPets);
            //close stream and file
            objectOut.close();
            fileOut.close();
            } catch(Exception E) {
                //if IOException or FileNotFoundException are thrown, alert user
                System.out.println("File could not be saved.");
        }
    }
}
