package com.mixology;

import com.mixology.database.Recipe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.mixology.RegisteredUsers.ReadcsvFile;

public class Favorites implements Serializable {
    private static final String faveFilePath = "data/favorites.dat";
    private static final String userFilePath = "src/com/mixology/MasterList.csv";

    public static Favorites getInstance() {
        Favorites favorites = null;

        if (Files.exists(Path.of(faveFilePath))) {
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(faveFilePath))) {
                favorites = (Favorites) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            favorites = new Favorites();
        }
        return favorites;
    }

//    private final Map<String, String> userNameMap = loadUserNameMap();

    private final Map<String, RegisteredUsers> favesMap = new TreeMap<>(); //

    private Favorites() {
    }

//    public void update(String firstName, Recipe recipeName) {
    public void update(String firstName, Recipe recipeName) {
        RegisteredUsers user = null;

        if (favesMap.containsKey(firstName)) {
            user = favesMap.get(firstName);
        }
//        else {
//            user = new RegisteredUsers(firstName, userNameMap.get(firstName));
//            favesMap.put(firstName, user);
//        }
        assert user != null;
        user.saveRecipe(recipeName);
        save();
    }

    public void showFaves() {
        if (favesMap.isEmpty()){
            System.out.println("Favorites are currently empty");
        } else {
            Collection<RegisteredUsers> users = favesMap.values();
            System.out.println("First Name      Last Name       Tagline         Recipe");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (RegisteredUsers user : users) {
                System.out.printf("%2s %9s %9s %30s \n",
                        user.getFirstName(), user.getLastName(), user.getTagline(), user.getRecipes());
            }
        }
    }

    private void loadUserNameMap() throws IOException {
        ReadcsvFile();
    }

//    private Map<String, String> loadUserNameMap() {
//        Map<String, String> idMap = new HashMap<>();
//
//        try {
//            List<String> lines = Files.readAllLines(Path.of(userFilePath));
//
//            //for each line, we "split" it on the comma into two "tokens"
//            for (String line : lines) {
//                String[] tokens = line.split(",");
//                Integer id = Integer.valueOf(tokens[0]);
//                String name = tokens[1];
//                idMap.put(id, name);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return idMap;
//    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(faveFilePath))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}