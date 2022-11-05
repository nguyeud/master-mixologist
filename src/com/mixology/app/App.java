package com.mixology.app;

import com.apps.util.Prompter;
import com.mixology.RegisteredUsers;
import com.mixology.UnregisteredUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final Prompter prompter = new Prompter(new Scanner(System.in));
//    private final RegisteredUsers registered = RegisteredUsers.getInstance();
    private final RegisteredUsers registered = RegisteredUsers.getInstance();
    private final String guest = UnregisteredUser.getName();

    public void execute() {
        welcome();
//        String user = userSelection(); // Register, or cont as guest
        userSelection(); // Register, or cont as guest
        String name = promptForName();
        String tagline = promptForTagline();
//        update(String name, String tagline)
//        search();
//        showResults();
//        save(); // would you like to save
//        restart(); // prompt User to search again
        goodbye();



    }

    private void welcome() {
        try {
            String greeting = Files.readString(Paths.get("WelcomeToMasterMixologist.txt"));
            System.out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userSelection() {

        String user = null;

        String input = prompter.prompt(
                "Are you a registered user? Y/N ",
                "\\s*" + "(y|Y|n|N)" + "\\s*", ""
        ).trim().toUpperCase();

        // TODO
//        user = ("Y".equals(input)) ? String.valueOf(registered) : guest;
    }

    private String promptForName() {

        System.out.println("Please enter your name: ");

        String userName = scanner.nextLine();  // Read user input
        System.out.println("Username is: " + userName);  // Output user input

        return userName;
    }

    private String promptForTagline() {

        System.out.println("Please enter your tagline: ");

        String tagline = scanner.nextLine();
        System.out.println("Tagline: " + tagline);

        return tagline;
    }

//    private void search() {
//    }
//
//    private void showResults() {
//    }
//
//    private void save() {
//    }
//
//    private void restart() {
//    }

    private void goodbye() {
        try {
            String greeting = Files.readString(Paths.get("Goodbye.txt"));
            System.out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}