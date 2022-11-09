package com.mixology.database;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RecipeTest {

    String id = "11007";
    String name = "Margarita";
    String category = "Ordinary Drink";
    List<String> ingredientList = List.of(new String[]{"Tequila", "Triple sec", "Lime juice"});
    String instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it.\n" +
            "      Take care to moisten only the outer rim and sprinkle the salt on it. The\n" +
            "      salt should present to the lips of the imbiber and never mix into the\n" +
            "      cocktail. Shake the other ingredients with ice, then carefully pour into\n" +
            "      the glass.";
    boolean isAlcoholic = true;
    String image = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg";

    Recipe recipe = new Recipe(id, name, category, ingredientList, instructions, isAlcoholic, image);

    //Valid test...
    @Test
    public void showSameID() {
        String expectedid = recipe.getId();
        assertSame("11007", expectedid);
    }

    @Test
    public void showSameName() {
        String expectedName = recipe.getName();
        assertSame("Margarita", expectedName);
    }

    @Test
    public void showSameCategory() {
        String expectedCategory = recipe.getCategory();
        assertSame("Ordinary Drink", expectedCategory);
    }

    @Test
    public void showSameisAlcoholic() {
        boolean expectedisAlcoholic = recipe.isAlcoholic();
        assertSame(true, expectedisAlcoholic);
    }

    @Test
    public void showSameinstructions() {
        String expectedinstructions = recipe.getInstructions();
        assertSame("Rub the rim of the glass with the lime slice to make the salt stick to it.\n" +
                "      Take care to moisten only the outer rim and sprinkle the salt on it. The\n" +
                "      salt should present to the lips of the imbiber and never mix into the\n" +
                "      cocktail. Shake the other ingredients with ice, then carefully pour into\n" +
                "      the glass.", expectedinstructions);
    }

    @Test
    public void showSameURL() {
        String expectedURL = recipe.getImage();
        assertSame("https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg", expectedURL);
    }

    @Test
    public void showSameIngrediants() {
        List<String> expectediIngredientList = List.of(new String[]{"Tequila", "Triple sec", "Lime juice"});
        assertEquals(recipe.getIngredientList(), expectediIngredientList);
    }

    //Invalid test
    @Test
    public void showDiffernetID() {
        String expectedid = recipe.getId();
        assertNotSame("000", expectedid);
    }

    @Test
    public void showDifferentName() {
        String expectedid = recipe.getName();
        assertNotSame("Blue Margarita", expectedid);
    }

    @Test
    public void showDifferentCategory() {
        String expectedCategory = recipe.getCategory();
        assertNotSame("Drink", expectedCategory);
    }

    @Test
    public void showDifferentinstructions() {
        String expectedinstructions = recipe.getInstructions();
        assertNotSame("Break glass when empty", expectedinstructions);
    }

    @Test
    public void showDifferentisAlcoholic() {
        boolean expectedisAlcoholic = recipe.isAlcoholic();
        assertNotSame(false, expectedisAlcoholic);
    }

    @Test
    public void showDifferentURL() {
        String expectedURL = recipe.getImage();
        assertNotSame("https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg", expectedURL);
    }

    @Test
    public void showDifferentIngrediants() {
        List<String> expectediIngredientList = List.of(new String[]{"Tequila", "Triple sec", "Lemon juice"});
        assertNotEquals(recipe.getIngredientList(), expectediIngredientList);
    }

    @Test
    public void showOutofBoundsLowerID() {
        String expectedid = recipe.getId();
        assertNotSame("-1", expectedid);
    }

    @Test
    public void showOutofBoundsUpperID() {
        String expectedid = recipe.getId();
        assertNotSame("99999999", expectedid);
    }
}