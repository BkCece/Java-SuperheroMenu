package com.ckwright.assignment1;

import java.util.ArrayList;
import java.util.List;

//Class to display text menu
public class Menu {

    String menuTitle;
    List<String> arrMenuOptions = new ArrayList<>();

    //Method to print menu to the screen
    public void printMenu(){

        //Print menu title with a border of *
        printStarRow();
        System.out.println("* " + menuTitle + " *");
        printStarRow();

        //Print menu options


    }//printMenu

    //to print a row of stars with adjusted length
    public void printStarRow(){

        for (int i = -1; i < menuTitle.length() + 3; i++){
            //append an extra 2 *'s before and after by initializing with -1 and comparing with +3
            System.out.print("*");
        }//for
        System.out.println();

    }//printStarRow

    //Constructor
    public Menu(String menuTitle, List<String> arrMenuOptions) {
        this.menuTitle = menuTitle;
        this.arrMenuOptions = arrMenuOptions;
    }

    //Getters and setters
    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public List<String> getArrMenuOptions() {
        return arrMenuOptions;
    }

    public void setArrMenuOptions(List<String> arrMenuOptions) {
        this.arrMenuOptions = arrMenuOptions;
    }
}//Menu
