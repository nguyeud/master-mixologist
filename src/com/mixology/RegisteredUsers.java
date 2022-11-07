package com.mixology;

import java.io.*;
import java.util.*;

public class RegisteredUsers implements Comparable<RegisteredUsers> {
    /*  Change the path statement in the next line to your file location */
//    private static final String csvFilePath = "master-mixologist\\src\\data\\MasterList.csv";
    private static final String csvFilePath = "src/com/mixology/MasterList.csv";


    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";

    private String firstName;
    private String lastName;
    private String tagline;
    private static Set<RegisteredUsers> users = new TreeSet<>();

    public static void VerifyUser(RegisteredUsers newUser) throws FileNotFoundException {
        if (!users.add(newUser)) {
            System.out.println("Welcome back " + newUser.getFirstName() + " "
                    + newUser.getLastName() + " " + "(" + newUser.getTagline() + ")");
        } else {
            String toJoin = "N";
            Scanner input = new Scanner(System.in);
            System.out.print("Would you like to join? (Y|N) ");
            toJoin = input.next();
            if(Objects.equals(toJoin.toUpperCase(), "Y")) {
                users.add(newUser);
                System.out.println("Welcome new member! " + newUser.getFirstName() + " "
                        + newUser.getLastName() + " " + "(" + newUser.getTagline() + ")");
                WriteTreeSetTocsv((TreeSet) users);
            }
        }
    }

    public static void ReadcsvFile() throws FileNotFoundException {
        File file = new File(csvFilePath);

        try {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String line = inputStream.nextLine();
                String[] firstLastTagline = line.split(",");
                users.add(new RegisteredUsers(firstLastTagline[0], firstLastTagline[1], firstLastTagline[2]));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ClearcsvFile();

        //System.out.println(users);

        WriteTreeSetTocsv((TreeSet) users);

    }

    private static void ClearcsvFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(csvFilePath);
        writer.print("");
        writer.close();
    }

    private static void WriteTreeSetTocsv(TreeSet users) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(csvFilePath);
        Iterator it = users.iterator();
        while (it.hasNext()) {
            RegisteredUsers u = (RegisteredUsers) it.next();
            writer.append(u.getFirstName());
            writer.append(DELIMITER);
            writer.append(u.getLastName());
            writer.append(DELIMITER);
            writer.append(u.getTagline());
            writer.append(SEPARATOR);
        }
        writer.close();
    }

    public RegisteredUsers(String firstName, String lastName, String tagline) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tagline = tagline;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @Override
    public String toString() {
        return " firstName=" + getFirstName() + " lastName=" + getLastName() + " tagline=" + getTagline() + "\n";
    }

    @Override
    public int compareTo(RegisteredUsers secondName) {
        return firstName.compareTo(secondName.getFirstName());
    }
}