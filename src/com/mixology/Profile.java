package com.mixology;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

class Profiles implements Serializable {

    private static final String dataFilePath = "data/profiles-master-list.dat";

    private static Set<String> registeredUserSet = new HashSet<>();

    // No outside instantiation - getInstance() is the sole access point
    private Profiles() {}

    public static Profiles getInstance() {
        Profiles profile = null;

        loadFilePath = "";

        if (Files.exists(Path.of(loadFilePath))) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(loadFilePath))) {
                profile = (profile) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            profile = new Profiles();
        }

        return profile;
    }

    private void saveProfile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
