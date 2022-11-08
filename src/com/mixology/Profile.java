package com.mixology;

import com.mixology.database.Recipe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1234L;

    private static String dataFilePath;
    private static String identifier;

    private final Map<String, User> profileMap  = new HashMap<>();

    // No outside instantiation - getInstance() is the sole access point
    private Profile() {}

    public static Profile getInstance(String firstName, String lastName, String tagline) {
        Profile profile = null;

        dataFilePath = getFilePath(firstName, lastName, tagline);

        identifier = createIdentifier(firstName, lastName, tagline);

        if (Files.exists(Path.of(dataFilePath))) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFilePath))) {
                profile = (Profile) in.readObject();

                System.out.printf("WELCOME BACK %s %s #%s! :)\n", firstName.toUpperCase(), lastName.toUpperCase(),
                        tagline.toUpperCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            profile = new Profile();

            System.out.println("WELCOME NEW MEMBER! THANK YOU FOR REGISTERING! :).\n");
        }

        return profile;
    }

    private static String getFilePath(String firstName, String lastName, String tagline) {
        String filePath = "data/";

        filePath += firstName + "-" + lastName + "-" + tagline + ".dat";

        return filePath;
    }

    private static String createIdentifier(String firstName, String lastName, String tagline) {
        identifier = firstName + "-" + lastName + "-" + tagline;

        return identifier;
    }

    public void updateProfile(String firstName, String lastName, String tagLine, Recipe recipe, String id) {
        User user = null;

        if (profileMap.containsKey(identifier)) {
            user = profileMap.get(identifier);
        } else {
            user = new User(identifier, firstName, lastName, tagLine);
            profileMap.put(identifier, user);
        }

        if ("S".equals(id)) {
            user.saveRecipe(recipe);
        } else if ("D".equals(id)) {
            System.out.println(recipe);
            user.deleteRecipe(recipe);
        }

        saveProfile();
    }

    private void saveProfile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean showFavorites() {
        User user = profileMap.get(identifier);
        List<Recipe> recipes = user.getRecipes();
        boolean isEmpty = false;

        if (user.getRecipes().isEmpty()) {
            System.out.println("You do not have any saved favorites!");
            isEmpty = true;
        } else {
            System.out.println("FAVORITE COCKTAILS");
            System.out.println("==================");

            List<Integer> stringLength = new ArrayList<>();
            for (Recipe recipe : recipes) {
                stringLength.add(recipe.getName().length());
            }

            int maxLength = Collections.max(stringLength);

            System.out.printf("%-8s | %-10s\n", "ID", "Cocktail");
            String border = "=";
            System.out.printf("=========|%s\n", border.repeat(maxLength));
            for (Recipe recipe : user.getRecipes()) {
                System.out.printf("%-8s | %-10s\n", recipe.getId(), recipe.getName());
            }
        }

        return isEmpty;
    }

    public Map<String, String> getFavorites() {
        Map<String, String> map = new TreeMap<>();

        User user = profileMap.get(identifier);
        List<Recipe> recipes = user.getRecipes();

        for (Recipe recipe : user.getRecipes()) {
            map.put(recipe.getId(), recipe.getName());
        }

        return map;
    }
}
