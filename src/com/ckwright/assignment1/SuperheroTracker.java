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

    //Global list
    public static List<Superhero> superheroes = new ArrayList<>();

    //Globals for Scanner
    public static Scanner input = new Scanner(System.in);
    public static int choice;
    public static String strChoice;

    //Print all heroes
    public static void displayAllHeroes() {
        for (int i = 0; i < superheroes.size(); i++) {
            System.out.println(i + 1
                    + ". Hero name: " + superheroes.get(i).getName()
                    + ", Height: " + superheroes.get(i).getHeightCM()
                    + ", Superpower: " + superheroes.get(i).getSuperpower()
                    + ", Saved " + superheroes.get(i).getNumCiviliansSaved() + " civilians."
            );
        }
    }

    //Add new heroes
    public static void addSuperhero() {
        //Get new superhero data
        System.out.print("Enter Hero's name: ");
        String name = input.next();

        System.out.print("Enter Hero's height in cm: ");
        int height = input.nextInt();

        System.out.print("Enter Hero's Superpower: ");
        String power = input.next();

        //Add new superhero
        superheroes.add(new Superhero(name, power, height, 0));

        //Display new superhero
        int newHeroIndex = superheroes.size() - 1;
        System.out.println(superheroes.get(newHeroIndex).getName() + " has been added to the list of superheroes.");
    }

    //Remove a superhero
    public static void removeSuperhero() {
        //Loop while choice is invalid/not exit
        do{
            System.out.println("Enter hero number to be removed or enter 0 to cancel");
            System.out.print("Enter >>");
            strChoice = input.next();

            //Test for erroneous input
            try {
                choice = Integer.parseInt(strChoice);
            } catch (Exception ex) {
                System.out.println("Please enter a valid hero number");
                choice = 0;
            }
        }while((choice > superheroes.size()) || (choice == 0) || (choice < 0));

        //if not quit
        if(choice != 0){
            //Remove hero & display to screen
            System.out.println(superheroes.get(choice - 1).getName() + " has been removed from the list of superheroes.");
            superheroes.remove(choice - 1);
        }
    }

    //Update num civilians
    public static void updateCivilians() {
        //Loop until valid choice is made
        do {
            System.out.println("Enter hero number to update civilian count or enter 0 to cancel");
            System.out.print("Enter >>");
            strChoice = input.next();

            //Test for erroneous input
            try {
                choice = Integer.parseInt(strChoice);
            } catch (Exception ex) {
                System.out.println("Please enter a valid hero number");
                choice = 0;
            }

        } while ((choice > superheroes.size()) || (choice == 0) || (choice < 0));

        //Only proceed if not 0
        if (choice != 0) {
            int count = -1;

            //Loop until valid input
            do {
                System.out.println("Enter new civilian save count");
                strChoice = input.next();
                try {
                    count = Integer.parseInt(strChoice);
                } catch (Exception ex) {
                    System.out.println("Please enter a valid number");
                }
            } while (count == -1);

            //Display new count
            int previousNumSaved = superheroes.get(choice - 1).getNumCiviliansSaved();
            superheroes.get(choice - 1).setNumCiviliansSaved(count);
            System.out.println("Number of civilians saved by " + superheroes.get(choice - 1).getName()
                    + " has been updated from " + previousNumSaved + " to " + superheroes.get(choice - 1).getNumCiviliansSaved());

            //Write new data to json file ******

        }
    }

    //Get top 3 superheroes
    public static void getTopSuperheroes(int n){
        //Array for top heroes
        //Size is flexible, depends on parameter
        List<Superhero> arrSorted = new ArrayList<>();
        arrSorted = superheroes;

        //Loop through to sort heroes in our temp list
        for(int i = 0; i < arrSorted.size(); i++){
            for (int j = i+1; j < arrSorted.size(); j++){
                if(arrSorted.get(i).getNumCiviliansSaved() < arrSorted.get(j).getNumCiviliansSaved()){
                    Collections.swap(arrSorted, i, j);
                }
            }
        }

        //Get top 3
        if(arrSorted.size() < 3){
            System.out.println("Not enough superheroes for a Top 3 List!");

        //If any of top 3 are 0 saved, not enough heroes
        }else if(arrSorted.get(0).getNumCiviliansSaved() == 0 ||
                arrSorted.get(1).getNumCiviliansSaved() == 0 ||
                arrSorted.get(2).getNumCiviliansSaved() == 0){

            System.out.println("The superheroes have not saved enough civilians.");
        }else{
            System.out.println("1. " + arrSorted.get(0).getName() + " has saved " + arrSorted.get(0).getNumCiviliansSaved() + " civilians.");
            System.out.println("2. " + arrSorted.get(1).getName() + " has saved " + arrSorted.get(1).getNumCiviliansSaved() + " civilians.");
            System.out.println("3. " + arrSorted.get(2).getName() + " has saved " + arrSorted.get(2).getNumCiviliansSaved() + " civilians.");
        }
    }

    //Simplify main code by extracting in separate method
    public static void extractDatabase(File f, List l) {
        //Check for Json file to fill database with
        try {

            //Json file reading from Brain Fraser's tutorial
            //https://youtu.be/HSuVtkdej8Q
            JsonElement fileElement = JsonParser.parseReader(new FileReader(f));
            JsonObject fileObject = fileElement.getAsJsonObject();

            //Extract all superheroes
            JsonArray jArrayOfHeroes = fileObject.get("superheroes").getAsJsonArray();
            //List<Superhero> superheroes = new ArrayList<>();
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

                //Double heightCM = null;
                //if(heroJsonObject.has("heightCM")){
                Double heightCM = heroJsonObject.get("heightCM").getAsDouble();
                //}

                //Integer numCiviliansSaved = null;
                //if(heroJsonObject.has("numSaved")){
                Integer numCiviliansSaved = heroJsonObject.get("numSaved").getAsInt();
                //}

                Superhero superhero = new Superhero(name, superpower, heightCM, numCiviliansSaved);
                l.add(superhero);

            }


            //TEST PRINT RESULTS
            //System.out.println("All of my superheroes are, from method: " + l);

        } catch (FileNotFoundException e) {
            //If no database yet, create Json file
            System.err.println("Error input file not found"); //temp
            e.printStackTrace(); //temp
            // ^ delete later
        }
    }

    public static void main(String[] args) {
        //Get Json file - database
        //from gson library at com.google.code.gson
        File superheroData = new File("src/com/ckwright/assignment1/SuperheroData.json");

        //Extract database to list - database
        extractDatabase(superheroData, superheroes);

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

        choice = -1;
        //Check if exit
        do {
            //Print menu & request user input
            superheroMenu.printMenu();
            System.out.print("Enter >>");
            strChoice = input.next();

            try {
                choice = Integer.parseInt(strChoice);

                //Menu actions
                switch (choice) {
                    //List all
                    case 1:
                        displayAllHeroes();
                        break;

                    //Add new
                    case 2:
                        addSuperhero();
                        break;

                    //Remove
                    case 3:
                        removeSuperhero();
                        break;

                    //Update civilians
                    case 4:
                        displayAllHeroes();
                        updateCivilians();
                        break;

                    //List top 3
                    case 5:
                        getTopSuperheroes(3);
                        break;

                    //Debug dump
                    case 6:
                        System.out.println("All of my superheroes are, out of method: " + superheroes);
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

    }//main

}//SuperheroTracker