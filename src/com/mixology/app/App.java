package com.mixology.app;

import com.apps.util.Prompter;
import com.mixology.Favorites;
import com.mixology.RegisteredUsers;
import com.mixology.UnregisteredUser;
import com.mixology.database.IdRequest;
import com.mixology.database.IngredientRequest;
import com.mixology.database.Recipe;
import com.mixology.database.SearchRequest;
import com.mixology.database.SearchRequest.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.mixology.RegisteredUsers.ReadcsvFile;
import static com.mixology.RegisteredUsers.VerifyUser;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final String guest = UnregisteredUser.getName();

    private final Favorites favorites = Favorites.getInstance();



    public void execute() throws IOException {
        welcome();
        registeredNew();
//        search();
//        showResults();
//        save(); // would you like to save
//        restart(); // prompt User to search again
        promptForActionFromHome();
//        goodbye();
    }

    private void welcome() {
        try {
            String greeting = Files.readString(Paths.get("WelcomeToMasterMixologist.txt"));
            System.out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registeredNew() throws IOException {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter user's first name: ");
        String first = keyboard.next();

        System.out.print("Enter user's last name: ");
        String last = keyboard.next();

        System.out.print("Enter user's tagline: ");
        String tag = keyboard.next();

        RegisteredUsers newUser =new RegisteredUsers(first, last, tag);

        ReadcsvFile();
        VerifyUser(newUser);
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
                        promptForSearchType();
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

    private  void promptForActionFromSearch(Map<String, String> cocktails) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do?\nEnter the ID of the drink to show the recipe, [B]" +
                    " to go back to search, [H] to go home, or [E] to exit: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("B|H|E")) {
                validInput = true;

                switch (input) {
                    case "B":
                        promptForSearchType();
                        break;
                    case "H":
                        promptForActionFromHome();
                        break;
                    case "E":
                        goodbye();
                        break;
                }
            } else {
                promptForId(input, cocktails);
                validInput = true;
            }
        }
    }

    private  void promptForActionFromSRecipe(Recipe recipe) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do?\nEnter [S] to save the recipe, [B]" +
                    " to go back to search, [H] to go home, or [E] to exit: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("B|H|S|E")) {
                validInput = true;

                switch(input) {
                    case "B":
                        promptForSearchType();
                        break;
                    case "H":
                        promptForActionFromHome();
                        break;
                    case "S":
                        // TODO: favorites.update(); // working on this
                        break;
                    case "E":
                        goodbye();
                        break;
                }
            }
        }
    }

    private void promptForSearchType() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Would you like to search by [C]ocktail name or [I]ngredient: ");
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
            System.out.println("Enter cocktail name: ");
            String input = scanner.nextLine().trim();

            cocktails = SearchRequest.sendRequest(input);

            if (!cocktails.isEmpty()) {
                showCocktail(input, cocktails);
                validInput = true;
            } else {
                System.out.printf("No search results for %s were found. ", input);
            }
        }
    }

    private  void promptForIngredient() {
        Map<String, String> cocktails = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter an ingredient: ");
            String input = scanner.nextLine().trim();

            cocktails = IngredientRequest.sendRequest(input);

            if (!cocktails.isEmpty()) {
                showCocktail(input, cocktails);
                validInput = true;
            } else {
                System.out.printf("No search results for %s were found. ", input);
            }
        }
    }

    private  void showCocktail(String input, Map<String, String> cocktails) {
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

    private void promptForId(String input, Map<String, String> cocktails) {
        Recipe recipe = null;
        boolean validInput = false;

        while (!validInput) {
            if (cocktails.containsKey(input)) {
                recipe = IdRequest.sendRequest(Integer.parseInt(input));
                validInput = true;
            } else {
                promptForActionFromSearch(cocktails);
            }
        }
        showRecipe(input, recipe);
    }

    private void showRecipe(String input, Recipe recipe) {
        System.out.println(recipe.getName().toUpperCase() + " RECIPE");

        String border = "=";
        System.out.printf("=======%s\n", border.repeat(recipe.getName().length()));

        System.out.printf("Category: %s\n", recipe.getCategory());
        System.out.printf("Alcoholic: %s\n", recipe.isAlcoholic());
        System.out.println("Ingredients:");
        for (int i = 0; i < recipe.getIngredientList().size(); i++) {
            String point = String.valueOf(i + 1) + ". ";
            System.out.println(point + recipe.getIngredientList().get(i));
        }
        System.out.printf("Instructions: %s\n", recipe.getInstructions());

        promptForActionFromSRecipe(recipe);
    }


//    private void showResults() {
//    }
//
//    private void save() {
//    }

//    private void restart() {
//        String input = prompter.prompt(
//                "Would you like to search again? Y/N "
//        ).trim().toUpperCase();
//
//        if ("Y".equals(input)){
//            search();
//            restart();
//        }
//    }

    private void update(String firstName, Recipe recipeName) {
        favorites.update(firstName, recipeName);
    }

    private  void showFaves() {
        favorites.showFaves();
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