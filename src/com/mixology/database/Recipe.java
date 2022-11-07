package com.mixology.database;

import java.util.List;

public class Recipe {
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
