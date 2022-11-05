package com.mixology;

public class UnregisteredUser implements User {
    private static String name;

    @Override
    public void search() {
    }

    public static String getName() {
        return name = "Guest";
    }

    //    public void guestHome() {
//        // general search
//    }
}