package com.mixology;

import com.mixology.database.Recipe;
import com.apps.util.Console;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.mixology.app.App.*;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1234L;
    private final Map<String, User> profileMap  = new HashMap<>();

    private static String dataFilePath;
    private static String identifier;

    private static String getFilePath(String firstName, String lastName, String tagline) {
        String filePath = "data/";

        filePath += firstName + "-" + lastName + "-" + tagline + ".dat";

        return filePath;
    }

    private static String createIdentifier(String firstName, String lastName, String tagline) {
        identifier = firstName + "-" + lastName + "-" + tagline;

        return identifier;
    }

    // No outside instantiation - getInstance() is the sole access point
    private Profile() {}

    // Get instance of Profile at welcome screen
    public static Profile getInstance(String firstName, String lastName, String tagline) {
        Profile profile = null;

        dataFilePath = getFilePath(firstName, lastName, tagline);

        identifier = createIdentifier(firstName, lastName, tagline);

        if (Files.exists(Path.of(dataFilePath))) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFilePath))) {
                profile = (Profile) in.readObject();

                System.out.printf(ANSI_BRIGHT_PURPLE + "\nWELCOME BACK %s %s #%s! :)\n\n" + ANSI_RESET,
                        firstName.toUpperCase(),
                        lastName.toUpperCase(),
                        tagline.toUpperCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            profile = new Profile();

            System.out.println(ANSI_BRIGHT_PURPLE + "\nWELCOME NEW MEMBER! THANK YOU FOR REGISTERING! :)\n" + ANSI_RESET);
        }

        return profile;
    }

    // Verify if the profile exists as a .dat file
    public static boolean verifyProfileExistence() {
        boolean exists = false;

        if (Files.exists(Path.of(dataFilePath))) {
            exists = true;
        }

        return exists;
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
        boolean isEmpty = false;

        if (user.getRecipes().isEmpty()) {
            System.out.println(ANSI_RED + "\nYou do not have any saved favorites!\n" + ANSI_RESET);
            isEmpty = true;
        } else {
            Console.clear();

            System.out.println(ANSI_YELLOW + "\nFAVORITE COCKTAILS");
            System.out.println("==================");

            List<Integer> stringLength = new ArrayList<>();
            for (Recipe recipe : user.getRecipes()) {
                stringLength.add(recipe.getName().length());
            }

            int maxLength = Collections.max(stringLength);

            System.out.printf("%-8s | %-10s\n", "ID", "Cocktail");
            String border = "=";
            System.out.printf("=========|%s\n", border.repeat(maxLength));
            for (Recipe recipe : user.getRecipes()) {
                System.out.printf("%-8s | %-10s\n" + ANSI_RESET, recipe.getId(), recipe.getName());
            }

            System.out.println();
        }

        return isEmpty;
    }

    public Map<String, String> getFavorites() {
        Map<String, String> map = new TreeMap<>();

        User user = profileMap.get(identifier);

        for (Recipe recipe : user.getRecipes()) {
            map.put(recipe.getId(), recipe.getName());
        }

        return map;
    }
}