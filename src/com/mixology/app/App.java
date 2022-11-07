package com.mixology.app;

import com.apps.util.Prompter;
import com.mixology.RegisteredUsers;
import com.mixology.UnregisteredUser;
import com.mixology.database.SearchRequest;
import com.mixology.database.SearchRequest.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import static com.mixology.RegisteredUsers.ReadcsvFile;
import static com.mixology.RegisteredUsers.VerifyUser;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final String guest = UnregisteredUser.getName();


    public void execute() throws IOException {
        welcome();
        registeredNew();
        search();
//        showResults();
//        save(); // would you like to save
        restart(); // prompt User to search again
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


    private void search() {
        Scanner search = new Scanner(System.in);

        System.out.print("Search by drink name: ");
        String drink = search.next();

        Map<String, String> result = SearchRequest.sendRequest(drink);
        System.out.println(result);

    }

//    private void showResults() {
//    }
//
//    private void save() {
//    }

    private void restart() {
        String input = prompter.prompt(
                "Would you like to search again? Y/N "
        ).trim().toUpperCase();

        if ("Y".equals(input)){
            search();
            restart();
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