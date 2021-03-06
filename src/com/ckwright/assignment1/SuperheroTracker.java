package com.ckwright.assignment1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * SPRING 2020
 * Course: CMPT213
 * Student Number: 301380112
 **/

public class SuperheroTracker {

    //Globals for Scanner
    public static Scanner input = new Scanner(System.in);
    public static int choice;
    public static String strChoice;

    public static boolean fileDNE = true;

    //Print all heroes
    public static void displayAllHeroes(List<Superhero> l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.println(i + 1
                    + ". Hero name: " + l.get(i).getName()
                    + ", Height: " + l.get(i).getHeightCM()
                    + ", Superpower: " + l.get(i).getSuperpower()
                    + ", Saved " + l.get(i).getNumCiviliansSaved() + " civilians."
            );
        }
    }

    //Add new heroes
    public static void addSuperhero(List<Superhero> l) {
        //Get new superhero data
        String name = null;
        do {
            System.out.print("Enter Hero's name: ");
            name = input.nextLine().trim();
        } while ((name == null) || name.isEmpty());

        String strHeight = null;
        double height = 0;
        do {
            System.out.print("Enter Hero's height in cm: ");
            strHeight = input.nextLine().trim();
            try {
                height = Double.parseDouble(strHeight);
            }catch(Exception ex){
                height = 0;
            }
        }while(height <= 0 );

        String power = null;
        do {
            System.out.print("Enter Hero's Superpower: ");
            power = input.nextLine().trim();
        } while ((name == null) || name.isEmpty());

        //Try to add new superhero
        try {
            //Add new superhero
            l.add(new Superhero(name, power, height, 0));

            //Display new superhero
            int newHeroIndex = l.size() - 1;
            System.out.println(l.get(newHeroIndex).getName() + " has been added to the list of superheroes.");

        } catch (Exception ex) {
            System.out.println("New superhero couldn't be added!");
        }

    }

    //Remove a superhero
    public static void removeSuperhero(List<Superhero> l) {
        //Loop while choice is invalid/not exit
        do {
            System.out.println("Enter hero number to be removed or enter 0 to cancel");
            System.out.print("Enter >>");
            strChoice = input.next().trim();
            input.nextLine();

            //Test for erroneous input
            try {
                choice = Integer.parseInt(strChoice);
            } catch (Exception ex) {
                System.out.println("Please enter a valid hero number");
                choice = 0;
            }
        } while ((choice > l.size()) || (choice < 0));

        //if not quit
        if (choice != 0) {
            //If this is not the last superhero
            if (l.size() > 1) {
                try {
                    //Remove hero & display to screen
                    System.out.println(l.get(choice - 1).getName() + " has been removed from the list of superheroes.");
                    l.remove(choice - 1);
                } catch (Exception ex) {
                    System.out.println("Superhero couldn't be removed!");
                }
            } else {
                System.out.println("That's your only superhero! Try another menu option.");
            }
        }
    }

    //Update num civilians
    public static void updateCivilians(List<Superhero> l) {
        //Loop until valid choice is made
        do {
            System.out.println("Enter hero number to update civilian count or enter 0 to cancel");
            System.out.print("Enter >>");
            strChoice = input.next().trim();
            input.nextLine();

            //Test for erroneous input
            try {
                choice = Integer.parseInt(strChoice);
            } catch (Exception ex) {
                System.out.println("Please enter a valid hero number");
                choice = 0;
            }

        } while ((choice > l.size()) || (choice < 0));

        //Only proceed if not 0
        if (choice != 0) {
            int count = -1;

            //Loop until valid input
            do {
                System.out.println("Enter new civilian save count");
                strChoice = input.next().trim();
                input.nextLine();

                try {
                    count = Integer.parseInt(strChoice);
                } catch (Exception ex) {
                    System.out.println("Please enter a valid number");
                }
            } while (count < 0);

            try {
                //Display new count
                int previousNumSaved = l.get(choice - 1).getNumCiviliansSaved();
                l.get(choice - 1).setNumCiviliansSaved(count);
                System.out.println("Number of civilians saved by " + l.get(choice - 1).getName()
                        + " has been updated from " + previousNumSaved + " to " + l.get(choice - 1).getNumCiviliansSaved());

            } catch (Exception ex) {
                System.out.println("The number of civilians saved could not be updated!");
            }

        }
    }

    //Get top 3 superheroes
    public static void getTopSuperheroes(int n, List<Superhero> l) {
        try {
            //Array for top heroes
            //Size is flexible, depends on parameter
            //Temp list
            List<Superhero> arrSorted = new ArrayList<Superhero>();
            for (int i = 0; i < l.size(); i++) {
                arrSorted.add(new Superhero(
                        l.get(i).getName(),
                        l.get(i).getSuperpower(),
                        l.get(i).getHeightCM(),
                        l.get(i).getNumCiviliansSaved()));
            }

            //Loop through to sort heroes in a temp list
            for (int i = 0; i < arrSorted.size(); i++) {
                for (int j = i + 1; j < arrSorted.size(); j++) {
                    if (arrSorted.get(i).getNumCiviliansSaved() < arrSorted.get(j).getNumCiviliansSaved()) {
                        Collections.swap(arrSorted, i, j);
                    }
                }
            }

            //Get top 3
            if (arrSorted.size() < 3) {
                System.out.println("Not enough superheroes for a Top 3 List!");

                //If any of top 3 are 0 saved, not enough heroes
            } else if (arrSorted.get(0).getNumCiviliansSaved() == 0 ||
                    arrSorted.get(1).getNumCiviliansSaved() == 0 ||
                    arrSorted.get(2).getNumCiviliansSaved() == 0) {

                System.out.println("The superheroes have not saved enough civilians.");
            } else {
                System.out.println("1. " + arrSorted.get(0).getName() + " has saved " + arrSorted.get(0).getNumCiviliansSaved() + " civilians.");
                System.out.println("2. " + arrSorted.get(1).getName() + " has saved " + arrSorted.get(1).getNumCiviliansSaved() + " civilians.");
                System.out.println("3. " + arrSorted.get(2).getName() + " has saved " + arrSorted.get(2).getNumCiviliansSaved() + " civilians.");
            }
        } catch (Exception ex) {
            System.out.println("The top 3 list could not be printed!");
        }
    }

    //Simplify main code by extracting in separate method
    //Returns true if success, false if error
    public static boolean extractDatabase(File f, List<Superhero> l) {
        //Check for Json file to fill database with
        try {

            //Json file reading from Brain Fraser's tutorial
            //https://youtu.be/HSuVtkdej8Q
            JsonElement fileElement = JsonParser.parseReader(new FileReader(f));
            JsonObject fileObject = fileElement.getAsJsonObject();

            //Extract all superheroes
            JsonArray jArrayOfHeroes = fileObject.get("superheroes").getAsJsonArray();
            for (JsonElement heroElement : jArrayOfHeroes) {
                //Get JsonObject
                JsonObject heroJsonObject = heroElement.getAsJsonObject();

                //Extract data - heroes
                String name = heroJsonObject.get("name").getAsString();

                //Assume only required field is superhero name
                //Need default null values for the rest
                String superpower = null;
                if (heroJsonObject.has("superpower")) {
                    superpower = heroJsonObject.get("superpower").getAsString();
                }

                Double heightCM = heroJsonObject.get("heightCM").getAsDouble();

                Integer numCiviliansSaved = heroJsonObject.get("numSaved").getAsInt();

                Superhero superhero = new Superhero(name, superpower, heightCM, numCiviliansSaved);
                l.add(superhero);

            }
            return true;

        } catch (FileNotFoundException e) {
            //If no database yet, create Json file
            System.out.println("Could not find file!");
            return false;
        }
    }

    //Write new data to database in json file
    public static void writeToDatabase(List<Superhero> l, String path) {
        try {
            FileWriter superheroFile = new FileWriter(path);
            JsonObject objSuperheroes = new JsonObject();
            JsonArray arrSuperheroes = new JsonArray();

            for (int i = 0; i < l.size(); i++) {
                JsonObject objSuperheroInfo = new JsonObject();

                objSuperheroInfo.addProperty("name", l.get(i).getName());
                objSuperheroInfo.addProperty("superpower", l.get(i).getSuperpower());
                objSuperheroInfo.addProperty("heightCM", l.get(i).getHeightCM());
                objSuperheroInfo.addProperty("numSaved", l.get(i).getNumCiviliansSaved());

                arrSuperheroes.add(objSuperheroInfo);
            }

            objSuperheroes.add("superheroes", arrSuperheroes);

            try {
                superheroFile.write(objSuperheroes.toString());
                System.out.println("Database updated!");

            } catch (Exception ex) {
                System.out.println("Could not update database...");

            } finally {
                try {

                    superheroFile.flush();
                    superheroFile.close();

                } catch (Exception ex) {
                    System.out.println("Could not close file...");
                }
            }
        } catch (Exception ex) {
            System.out.println("Could not create file!");
        }
    }

    public static void main(String[] args) {
        //Set menu title and options
        //Contained entirely in separate method so we can reuse the Menu class in any other project
        ArrayList<String> superheroMenuOptions = new ArrayList<>();
        superheroMenuOptions.add("List all superheroes");
        superheroMenuOptions.add("Add new superhero");
        superheroMenuOptions.add("Remove a superhero");
        superheroMenuOptions.add("Update number of civilians saved by a superhero");
        superheroMenuOptions.add("List Top 3 superheroes");
        superheroMenuOptions.add("Debug Dump (toString)");
        superheroMenuOptions.add("Exit");

        //Create menu
        Menu superheroMenu = new Menu("SuperHero Tracker", superheroMenuOptions);
        List<Superhero> superheroes = new ArrayList<>();

        //Get Json file - database
        //from gson library at com.google.code.gson
        String filePath = "src/com/ckwright/assignment1/SuperheroData.json";


        File superheroData = new File(filePath);
        System.out.println("exist " + superheroData.exists());

        //Check if exit
        do {

            try {

                //Must add at least one superhero before accessing other menu options
                //Print menu & request user input
                superheroMenu.printMenu();

                System.out.print("Enter >>");
                strChoice = input.next().trim();
                input.nextLine();
                choice = Integer.parseInt(strChoice);

                //If the user doesn't want to quit
                if(choice != 7) {
                    //If the file doesn't exist
                    if (fileDNE) {
                        try {
                            //Attempt to add json data to a superhero list
                            extractDatabase(superheroData, superheroes);
                        } catch (Exception ex) {
                            System.out.println("No database yet, so let's create one!");
                            superheroData.createNewFile();
                            choice = 2;

                            fileDNE = false;
                        }
                    }

                    //If no heroes, force user to add one
                    if(superheroes.size() == 0){
                        System.out.println("Database is empty!");
                        choice = 2;
                    }
                }

                //File exists!
                fileDNE = false;

                //Menu actions
                switch (choice) {
                    //List all
                    case 1:
                        displayAllHeroes(superheroes);
                        break;

                    //Add new
                    case 2:
                        addSuperhero(superheroes);
                        break;

                    //Remove
                    case 3:
                        displayAllHeroes(superheroes);
                        removeSuperhero(superheroes);
                        break;

                    //Update civilians
                    case 4:
                        displayAllHeroes(superheroes);
                        updateCivilians(superheroes);
                        break;

                    //List top 3
                    case 5:
                        getTopSuperheroes(3, superheroes);
                        break;

                    //Debug dump
                    case 6:
                        for (int i = 0; i < superheroes.size(); i++) {
                            System.out.println(i + 1 + ". " + superheroes.get(i));
                        }

                        break;

                    //Exit!
                    case 7:
                        break;

                    default:
                        System.out.println("Please make a valid selection!");
                        break;
                }
                System.out.println("");

            } catch (Exception ex) {
                System.out.println("Please make a valid selection!");
            }

        } while (choice != 7);

        //Overwrite/write to file
        writeToDatabase(superheroes, filePath);

        input.close();


    }//main

}//SuperheroTracker