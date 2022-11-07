package com.mixology.app;

import com.mixology.database.IdRequest;
import com.mixology.database.Recipe;
import com.mixology.database.SearchRequest;

import java.util.*;

class SearchClient {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        promptForActionFromHome();
    }

    private static void promptForActionFromHome() {
        String action = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do? [S]earch or [F]avorites: ");

            // TODO: maybe add logic that the user can exit anytime from the program by typing "exit"

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("S|F")) {
                validInput = true;

                if ("S".equals(input)) {
                    promptForCocktail();
                } else {

                }
            }
        }

    }

    private static void promptForActionFromSearch(Map<String, String> cocktails) {
        String action = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do?\nEnter the ID of the drink to show the recipe, [B]" +
                    " to go back to search, or [H] to go home: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("B|H")) {
                validInput = true;

                if ("B".equals(input)) {
                    promptForCocktail();
                } else {
                    promptForActionFromHome();
                }
            } else {
                promptForId(input, cocktails);
                validInput = true;
            }
        }
    }

    private static void promptForActionFromSRecipe(Recipe recipe) {
        String action = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do?\nEnter [S] to save the recipe, [B]" +
                    " to go back to search, or [H] to go home: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("B|H|S")) {
                validInput = true;

                switch(input) {
                    case "B":
                        promptForCocktail();
                        break;
                    case "H":
                        promptForActionFromHome();
                        break;
                    case "S":
                        break;
                        // TODO: if [S], save recipe to their file
                }
            }
        }
    }

    private static void promptForCocktail() {
        Map<String, String> cocktails = null;

        System.out.println("Enter the name of the cocktail: ");
        String input = scanner.nextLine().trim();

        cocktails = SearchRequest.sendRequest(input);

        showCocktail(input, cocktails);
    }

    private static void showCocktail(String input, Map<String, String> cocktails) {
        System.out.println("Search results for: " + input);

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

        promptForActionFromSearch(cocktails);
    }

    private static void promptForId(String input, Map<String, String> cocktails) {
        Recipe recipe = null;
        boolean validInput = false;

        while (!validInput) {
            if (cocktails.keySet().contains(input)) {
                recipe = IdRequest.sendRequest(Integer.parseInt(input));
                validInput = true;
            } else {
                promptForActionFromSearch(cocktails);
            }
        }
        showRecipe(input, recipe);
    }

    private static void showRecipe(String input, Recipe recipe) {
        System.out.println(recipe.getName().toUpperCase() + " RECIPE");

        String border = "=";
        System.out.printf("=======%s\n", border.repeat(recipe.getName().length()));

        // TODO: Create print of recipe...
        System.out.println(recipe);

        promptForActionFromSRecipe(recipe);
    }
}
