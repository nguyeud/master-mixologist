package com.mixology.app;

import com.mixology.*;
import com.mixology.database.IdRequest;
import com.mixology.database.IngredientRequest;
import com.mixology.database.Recipe;
import com.mixology.database.SearchRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static Profile profile;

    private static String firstName;
    private static String lastName;
    private static String tagLine;

    public void execute() throws IOException {
        welcome();
        verify();
        promptForActionFromHome();
    }

    private void welcome() {
        try {
            String greeting = Files.readString(Paths.get("WelcomeToMasterMixologist.txt"));
            System.out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verify() throws IOException {
        Scanner keyboard = new Scanner(System.in);

        // This code logs the user's name and tagline
        System.out.print("Enter user's first name: ");
        firstName = keyboard.next();

        System.out.print("Enter user's last name: ");
        lastName = keyboard.next();

        System.out.print("Enter user's tagline: ");
        tagLine = keyboard.next();

        profile = Profile.getInstance(firstName, lastName, tagLine);
    }

    private void promptForActionFromHome() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do? [S]earch, [F]avorites, or [E]xit: ");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("S|F|E")) {
                validInput = true;

                switch (input) {
                    case "S":
                        promptForCocktail();
                        break;
                    case "F":
                        showFaves();
                        break;
                    case "E":
                        goodbye();
                        break;
                }
            }
        }
    }

    private void promptForActionFromSearch(Map<String, String> cocktails, String id) {
        boolean validInput = false;

        while (!validInput) {

            if ("F".equals(id)) {
                System.out.println("What would you like to do?\nEnter the ID of the drink to show the recipe," +
                        " [H] to go home, or [E] to exit: ");
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.matches("H|E")) {
                    validInput = true;

                    switch (input) {
                        case "H":
                            promptForActionFromHome();
                            break;
                        case "E":
                            goodbye();
                            break;
                    }
                } else {
                    promptForId(input, cocktails, id);
                    validInput = true;
                }
            } else {
                System.out.println("What would you like to do?\nEnter the ID of the drink to show the recipe, [B]" +
                        " to go back to search, [H] to go home, or [E] to exit: ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("B|H|E")) {
                    validInput = true;

                    switch (input) {
                        case "B":
                            promptForCocktail();
                            break;
                        case "H":
                            promptForActionFromHome();
                            break;
                        case "E":
                            goodbye();
                            break;
                    }
                } else {
                    promptForId(input, cocktails, id);
                    validInput = true;
                }
            }
        }
    }

    private void promptForActionFromRecipe(Recipe recipe, String id) {
        boolean validInput = false;

        while (!validInput) {

            if ("F".equals(id)) {
                System.out.println("What would you like to do?\nEnter [D] to delete the recipe, [F]" +
                        " to go back to favorites, [H] to go home, or [E] to exit: ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("D|F|H|E")) {
                    validInput = true;

                    switch(input) {
                        case "H":
                            promptForActionFromHome();
                            break;
                        case "E":
                            goodbye();
                            break;
                        case "F":
                            showFaves();
                            break;
                        case "D":
                            updateProfile(firstName, lastName, tagLine, recipe, "D");
                            System.out.println("Successfully deleted recipe from favorites.");
                            showFaves();
                            break;
                    }
                }
            } else {
                System.out.println("What would you like to do?\nEnter [S] to save the recipe, [B]" +
                        " to go back to search, [H] to go home, or [E] to exit: ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("B|H|S|E")) {
                    validInput = true;

                    switch(input) {
                        case "B":
                            promptForCocktail();
                            break;
                        case "H":
                            promptForActionFromHome();
                            break;
                        case "S":
                            updateProfile(firstName, lastName, tagLine, recipe, "S");
                            System.out.println("Successfully added recipe to favorites.");
                            promptForActionFromHome();
                            break;
                        case "E":
                            goodbye();
                            break;
                    }
                }
            }
        }
    }

    private void updateProfile(String firstName, String lastName, String tagLine, Recipe recipe, String id) {
        profile.updateProfile(firstName, lastName, tagLine, recipe, id);
    }

    private void promptForCocktail() {
        Map<String, String> cocktails = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter cocktail name: ");
            String input = scanner.nextLine().trim();

            cocktails = SearchRequest.sendRequest(input);

            if (!cocktails.isEmpty()) {
                showCocktail(input, cocktails, "S");
                validInput = true;
            } else {
                System.out.printf("No search results for %s were found. ", input);
            }
        }
    }

    private void promptForId(String input, Map<String, String> cocktails, String id) {
        Recipe recipe = null;
        boolean validInput = false;

        while (!validInput) {
            if (cocktails.containsKey(input)) {
                recipe = IdRequest.sendRequest(Integer.parseInt(input));
                validInput = true;
            } else {
                promptForActionFromSearch(cocktails, id);
            }
        }

        showRecipe(input, recipe, id);
    }

    private void showCocktail(String input, Map<String, String> cocktails, String id) {
        SearchRequest.showCocktail(input, cocktails);

        promptForActionFromSearch(cocktails, id);
    }

    private void showRecipe(String input, Recipe recipe, String id) {
        recipe.showRecipe(input, recipe);

        promptForActionFromRecipe(recipe, id);
    }

    private void showFaves() {
        if (profile.showFavorites()) {
            promptForActionFromHome();
        } else {
            promptForActionFromSearch(profile.getFavorites(), "F");
        }
    }

    private void goodbye() {
        try {
            String greeting = Files.readString(Paths.get("Goodbye.txt"));
            System.out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}