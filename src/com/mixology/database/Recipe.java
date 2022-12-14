package com.mixology.database;

import java.io.Serializable;
import java.util.List;

import static com.mixology.app.App.ANSI_YELLOW;

public class Recipe implements Serializable {
    private String id;
    private String name;
    private String category;
    private List<String> ingredientList;
    private String instructions;
    private boolean isAlcoholic;
    private String image;

    public Recipe(String id, String name, String category, List<String> ingredientList, String instructions,
                  boolean isAlcoholic, String image) {
        setId(id);
        setName(name);
        setCategory(category);
        setIngredientList(ingredientList);
        setInstructions(instructions);
        setAlcoholic(isAlcoholic);
        setImage(image);
    }

    public void showRecipe(String input, Recipe recipe) {
        System.out.println(ANSI_YELLOW + "\n" + recipe.getName().toUpperCase() + " RECIPE");

        String border = "=";
        System.out.printf("=======%s\n", border.repeat(recipe.getName().length()));

        System.out.printf("Category: %s\n", recipe.getCategory());
        System.out.printf("Alcoholic: %s\n", recipe.isAlcoholic());
        System.out.println("Ingredients:");
        for (int i = 0; i < recipe.getIngredientList().size(); i++) {
            String point = i + 1 + ". ";
            System.out.println(point + recipe.getIngredientList().get(i));
        }
        System.out.printf("Instructions: %s\n", recipe.getInstructions());

        System.out.println();

        RecipePopup.jFramePopup(recipe);
    }

    // Get a formatted String output in JFrame
    public String getIngredientOutput() {
        String result = "<html>";

        for (int i = 0; i < getIngredientList().size(); i++) {
            String point = i + 1 + ". ";
            result += "<br/>" + point + getIngredientList().get(i);
        }

        result += "</html>";

        return result;
    }

    // Get a formatted String output in JFrame
    public String getInstructionOutput() {
        String result = "<html>";

        result += getInstructions();

        result += "</html>";

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", ingredientList=" + getIngredientList() +
                ", instructions='" + getInstructions() + '\'' +
                ", isAlcoholic=" + isAlcoholic() +
                ", image='" + getImage() + '\'' +
                '}';
    }
}
