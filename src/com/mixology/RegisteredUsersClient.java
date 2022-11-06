package com.mixology;

import com.mixology.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static com.mixology.RegisteredUsers.*;

class RegisteredUsersClient {
    public static void main(String[] args) throws IOException {
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

}