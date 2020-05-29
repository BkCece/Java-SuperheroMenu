package com.ckwright.assignment1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * SPRING 2020
 * Course: CMPT213
 * Student Number: 301380112
 *
 */

public class SuperheroTracker {
    public static void main(String[] args){
        //Set menu title and options
        //Contained entirely in separate method so we can reuse the Menu class in any other project
        ArrayList<String> superheroMenuOptions = new ArrayList<>();
        superheroMenuOptions.add("List all superheroes");
        superheroMenuOptions.add("Add new superheroes");
        superheroMenuOptions.add("Remove a superhero");
        superheroMenuOptions.add("Update number of civilians saved by a superhero");
        superheroMenuOptions.add("List Top 3 superheroes");
        superheroMenuOptions.add("Debug Dump (toString)");
        superheroMenuOptions.add("Exit");

        //Create menu
        Menu superheroMenu = new Menu("SuperHero Tracker", superheroMenuOptions);
        superheroMenu.printMenu();

        //Get Json file - database
        //from gson library at com.google.code.gson
        File superheroData = new File("src/com/ckwright/assignment1/SuperheroData.json");
        List<Superhero> superheroes = new ArrayList<>();
        extractDatabase(superheroData, superheroes);

        //TEST PRINT RESULTS
        System.out.println("All of my superheroes are, out of method: " + superheroes);


    }//main

    //Simplify main code by extracting in separate method
    public static void extractDatabase(File f, List l)
    {
        //Check for Json file to fill database with
        try {

            //Json file reading from Brain Fraser's tutorial
            //https://youtu.be/HSuVtkdej8Q
            JsonElement fileElement = JsonParser.parseReader(new FileReader(f));
            JsonObject fileObject = fileElement.getAsJsonObject();

            //Extract all superheroes
            JsonArray jArrayOfHeroes = fileObject.get("superheroes").getAsJsonArray();
            //List<Superhero> superheroes = new ArrayList<>();
            for (JsonElement heroElement: jArrayOfHeroes){
                //Get JsonObject
                JsonObject heroJsonObject = heroElement.getAsJsonObject();

                //Extract data - heroes
                String name = heroJsonObject.get("name").getAsString();

                //Assume only required field is superhero name
                //Need default null values for the rest
                String superpower = null;
                if(heroJsonObject.has("superpower")){
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
    public static void createDatabase(){
        //create it!
    }

}//SuperheroTracker