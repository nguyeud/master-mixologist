package com.mixology.app;

import com.apps.util.Prompter;
import com.mixology.RegisteredUser;
import com.mixology.UnregisteredUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final Prompter prompter = new Prompter(new Scanner(System.in));


    public void execute() {
        welcome();
//        String user = userSelection(); // Register, or cont as guest
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

//    private String userSelection() {
//
//        String user = null;
//
//        String input = prompter.prompt(
//                "Would you like to register? Y/N ",
//                "\\s*" + "(y|Y|n|N)" + "\\s*", ""
//        ).trim().toUpperCase();
//
////        user = ("Y".equals(input)) ? RegisteredUser : UnregisteredUser;
//
//        return user;
//    }
//
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