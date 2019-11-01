/*
 * CSC 422
 * Adam Maser
 * 10/31/2019
 * assignment 2: version control practice with pet program (extended)
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
            } catch(IOException | ClassNotFoundException ex) {
                //alert user if there was an error loading pets
                System.out.println("Error loading pets from file.");
            }
            
        }
        Scanner stdin = new Scanner(System.in);
        System.out.println("Pet Database Program.");
        while (true) {
            System.out.print("\nWhat would you like to do?\n"
                + " 1) View all pets\n"
                + " 2) Add new pets\n"
                + " 3) Remove a pet\n"
                + " 4) Exit program\n"
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
                    removePet();
                    break;
                case 4:
                    saveFile();
                    System.exit(0);
                default:
                    System.out.println("Please enter valid option.");
                    break;
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
                
                //handle any input -type- issues with try-catch block
                try {
                    String petName = parseResponse.next();
                    int petAge = parseResponse.nextInt();
                    //if there are tokens remaining after name and age, input is invalid
                    //i.e. three inputs ("George, 11, Animal")
                    if (parseResponse.hasNext()) {
                        System.out.println("Error: " + response + " is not a valid input.");
                        continue;
                    }
                    //test age, if outside of range 1-20, reset loop
                    if (petAge < 1 || petAge > 20) {
                        System.out.println("Error: " + petAge + " is not a valid age.");
                        continue;
                    }
                    Pet newPet = new Pet(petName, petAge);
                    allPets.add(newPet);

                    petsAdded++;
                } catch (Exception ex) {
                    System.out.println("Error: " + response + " is not a valid input.");
                }
                
            }
        }
        System.out.println(petsAdded + " pets added.");
    }
    
    private static void removePet() {
        displayPets(); //display all pets before prompting user
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the pet ID you want to remove: ");
        int petToDelete = stdin.nextInt();
        //error handlin if petToDelete is not an ID in allPets
        try {
            //get pet
            Pet pet = allPets.get(petToDelete);
            System.out.println(pet.getName() + " " + pet.getAge() + " has been removed.");
            //delete pet
            allPets.remove(petToDelete);
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Error: ID " + petToDelete + " does not exist.");
        }
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
            } catch(IOException E) {
                //if IOException or FileNotFoundException are thrown, alert user
                System.out.println("File could not be saved.");
        }
    }
}
