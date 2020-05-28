package com.ckwright.assignment1;

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

        //Print menu
        superheroMenu.printMenu();

        //Check for Json file to fill database with

        //If no database yet, create Json file

        //Prompt user for superhero

    }//main
}//SuperheroTracker
