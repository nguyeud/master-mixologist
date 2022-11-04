package com.mixology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RegisteredUser implements User {
    private String name;
    private String tagline;

    // pseudo
    private List<RegisteredUsers> drinks = new ArrayList<>();

    public RegisteredUser(String name, String tagline) {
        setName(name);
        setTagline(tagline);
    }

    @Override
    public void search() {
    }

    //pseudo
    public void save(RegisteredUsers drink) {
        drinks.add(drink);
    }

    public void delete() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    //pseudo
//    public List<Database> getSaved() {
//        return Collections.unmodifiableList(drinks);
//    }

    @Override
    public String toString() {
        return String.format("%s: name = %s, tagline = %s\n",
                getClass().getSimpleName(), getName(), getTagline());
    }
}