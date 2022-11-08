package com.mixology;

import com.mixology.database.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String identifier;
    private String firstName;
    private String lastName;
    private String tagline;
    private final List<Recipe> recipes = new ArrayList<>();

    public User(String identifier, String firstName, String lastName, String tagline) {
        this.identifier = identifier;
        setFirstName(firstName);
        setLastName(lastName);
        setTagline(tagline);
    }

    public void saveRecipe(Recipe recipe) {
        boolean inList = recipes.stream().anyMatch(r -> r.getId().equals(recipe.getId()));

        if (inList) {
            System.out.println("\nRecipe is already in your favorites!\n");
        } else {
            recipes.add(recipe);
            System.out.println("\nSuccessfully added recipe to favorites.\n");
        }
    }

    public void deleteRecipe(Recipe recipe) {
        recipes.removeIf(recipes -> Objects.equals(recipes.getId(), recipe.getId()));
        System.out.println("\nSuccessfully deleted recipe from favorites.");
    }

    public String getIdentifier() {
        return getFirstName() + "-" + getLastName() + "-" + getTagline();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(recipes);
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "identifier=" + getIdentifier() + + '\'' +
                ", firstName=" + getFirstName() + '\'' +
                ", lastName=" + getLastName() + '\'' +
                ", tagline=" + getTagline() + '\'' +
                ", drinks=" + getRecipes() +
                '}';
    }
}
