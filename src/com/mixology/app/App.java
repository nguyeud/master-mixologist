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

import static com.mixology.Profile.verifyProfileExistence;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private Profile profile;

    private String firstName;
    private String lastName;
    private String tagLine;

    // For font colors
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001b[32;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001b[34;1m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001b[35;1m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void execute() throws IOException {
        welcome();
        verify();
        promptForActionFromHome();
    }

    private void welcome() {
        try {
            String greeting = Files.readString(Paths.get("resources/WelcomeToMasterMixologist.txt"));
            System.out.println(ANSI_BRIGHT_PURPLE + greeting + ANSI_RESET);
            System.out.println(ANSI_BRIGHT_PURPLE + "If you like cocktails but have a hard time searching or remembers cocktails you've made, " +
                    "this is the application for you.\nThis application searches through a web database to show you " +
                    "different cocktails based on your search and saves them for you in a favorites section if you happen" +
                    " to like them." + ANSI_RESET);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void verify() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        boolean validInput = false;

        // This code logs the user's name and tagline
        while (!validInput) {
            System.out.print(ANSI_BLUE + "Enter user's first name: ");
            firstName = keyboard.nextLine();

            if (firstName.length() > 0) {
                validInput = true;
            }
        }

        validInput = false;

        while (!validInput) {
            System.out.print("Enter user's last name: ");
            lastName = keyboard.nextLine();

            if (lastName.length() > 0) {
                validInput = true;
            }
        }

        validInput = false;

        while (!validInput) {
            System.out.print("Enter user's tagline: ");
            tagLine = keyboard.nextLine();

            if (tagLine.length() > 0) {
                validInput = true;
            }
        }

        profile = Profile.getInstance(firstName, lastName, tagLine);
    }

    private void promptForActionFromHome() {
        boolean validInput = false;
        boolean profileExists = verifyProfileExistence();

        while (!validInput) {
            if (profileExists) {
                System.out.print(ANSI_CYAN + "What would you like to do? [S]earch, [F]avorites, or [E]xit: " + ANSI_RESET);

                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("S|F|E")) {
                    validInput = true;

                    switch (input) {
                        case "S":
                            promptForSearch();
                            break;
                        case "F":
                            showFavorites();
                            break;
                        case "E":
                            goodbye();
                            break;
                    }
                }
            } else {
                System.out.print(ANSI_CYAN +"What would you like to do? [S]earch or [E]xit: " + ANSI_RESET);

                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("S|E")) {
                    validInput = true;

                    switch (input) {
                        case "S":
                            promptForSearch();
                            break;
                        case "E":
                            goodbye();
                            break;
                    }
                }
            }
        }
    }

    private void promptForActionFromSearch(Map<String, String> cocktails, String id) {
        boolean validInput = false;

        while (!validInput) {

            if ("F".equals(id)) {
                System.out.print(ANSI_CYAN + "What would you like to do?\nEnter the ID of the drink to show or " +
                        "delete the recipe, [H] to go home, or [E] to exit: " + ANSI_RESET);
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
                System.out.print(ANSI_CYAN + "What would you like to do?\nEnter the ID of the drink to show the recipe, [B]" +
                        " to go back to search, [H] to go home, or [E] to exit: " + ANSI_RESET);
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("B|H|E")) {
                    validInput = true;

                    switch (input) {
                        case "B":
                            promptForSearch();
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
                System.out.print(ANSI_CYAN + "What would you like to do?\nEnter [D] to delete the recipe, [F]" +
                        " to go back to favorites, [H] to go home, or [E] to exit: " + ANSI_RESET);
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
                            showFavorites();
                            break;
                        case "D":
                            updateProfile(firstName, lastName, tagLine, recipe, "D");
                            showFavorites();
                            break;
                    }
                }
            } else {
                System.out.print(ANSI_CYAN + "What would you like to do?\nEnter [S] to save the recipe, [B]" +
                        " to go back to search, [H] to go home, or [E] to exit: " + ANSI_RESET);
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("B|H|S|E")) {
                    validInput = true;

                    switch(input) {
                        case "B":
                            promptForSearch();
                            break;
                        case "H":
                            promptForActionFromHome();
                            break;
                        case "S":
                            updateProfile(firstName, lastName, tagLine, recipe, "S");
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

    private void promptForSearch() {
        boolean validInput = false;

        while (!validInput) {
            System.out.print(ANSI_CYAN + "Would you like to search by [C]ocktail name or [I]ngredient: " + ANSI_RESET);

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("C|I")) {
                validInput = true;

                switch (input) {
                    case "C":
                        promptForCocktail();
                        break;
                    case "I":
                        promptForIngredient();
                        break;
                }
            }
        }
    }

    private void promptForCocktail() {
        Map<String, String> cocktails = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(ANSI_CYAN + "Enter cocktail name: " + ANSI_RESET);
            String input = scanner.nextLine().trim();

            cocktails = SearchRequest.sendRequest(input);

            if (!cocktails.isEmpty()) {
                showCocktail(input, cocktails, "S");
                validInput = true;
            } else {
                System.out.printf(ANSI_RED + "No search results for %s were found. " + ANSI_RESET, input);
            }
        }
    }

    private void promptForIngredient() {
        Map<String, String> cocktails = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(ANSI_CYAN + "Enter ingredient name: " + ANSI_RESET);
            String input = scanner.nextLine().trim();

            cocktails = IngredientRequest.sendRequest(input);

            if (!cocktails.isEmpty()) {
                showCocktail(input, cocktails, "S");
                validInput = true;
            } else {
                System.out.printf(ANSI_RED + "No search results for %s were found. " + ANSI_RESET, input);
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
        System.out.println(ANSI_YELLOW+ "\nSEARCH RESULTS FOR: " + input.toUpperCase());

        System.out.println("===================");

        List<Integer> cocktailLength = new ArrayList<>();
        for (String cocktail : cocktails.values()) {
            cocktailLength.add(cocktail.length());
        }

        int maxLength = Collections.max(cocktailLength);

        System.out.printf("%-8s | %-10s\n", "ID", "Cocktail");
        String border = "=";
        System.out.printf("=========|%s\n", border.repeat(maxLength));

        for (Map.Entry<String, String> entry : cocktails.entrySet()) {
            System.out.printf("%-8s | %-10s\n", entry.getKey(), entry.getValue());
        }

        System.out.println();

        promptForActionFromSearch(cocktails, id);
    }

    private void showRecipe(String input, Recipe recipe, String id) {
        recipe.showRecipe(input, recipe);

        promptForActionFromRecipe(recipe, id);
    }

    private void showFavorites() {
        if (profile.showFavorites()) {
            promptForActionFromHome();
        } else {
            promptForActionFromSearch(profile.getFavorites(), "F");
        }
    }

    private void goodbye() {
        try {
            String greeting = Files.readString(Paths.get("resources/Goodbye.txt"));
            System.out.println(ANSI_BRIGHT_PURPLE + greeting + ANSI_RESET);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}