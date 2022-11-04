package com.mixology;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
 * This is a lookup table to determine if the individual is a registered user.
 * When a new user, registers for the first time, we need to enter his/her name
 * When a restored user enters, we validate his name in the list.
 */
public class RegisteredUsers implements Serializable {
    private static final String dataFilePath = "data/registeredUsers.dat";

    public static RegisteredUsers getInstance() {
        RegisteredUsers registeredUsers = null;

        if (Files.exists(Path.of(dataFilePath))) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFilePath))) {
                registeredUsers = (RegisteredUsers) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            registeredUsers = new RegisteredUsers();
        }
        return null;
    }

    /*  Chose HashMap unordered and no duplicate keys (registered usernames and taglines*/

    private final Map<String, String> nameToTagline = new HashMap<>();
    private final Map<String, String> tagLineToName = new HashMap<>();

    public void update(String name, String tagline) {
        nameToTagline.put(name, tagline);
        tagLineToName.put(tagline, name);

        save();
    }

    private RegisteredUsers() {
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}