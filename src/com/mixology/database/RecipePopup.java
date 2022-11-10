package com.mixology.database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class RecipePopup {

    /*
     * Create JFrame pop-up when a recipe is being viewed
     */
    static void jFramePopup(Recipe recipe) {
        try {
            // Set up JFrame
            JFrame frame = new JFrame("Recipe: " + recipe.getName());
            frame.setLayout(new GridBagLayout());
            frame.setSize(400, 800);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            // Header
            JLabel header = new JLabel(recipe.getName(), SwingConstants.CENTER);
            header.setFont(new Font("Arial", Font.BOLD, 18));
            c.gridwidth = 2;
            c.ipady = 20;
            c.gridx = 0;
            c.gridy = 0;
            frame.add(header, c);

            // Image
            Image jFrameImage = ImageIO.read(new URL(recipe.getImage()));
            Image scaledImage = jFrameImage.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(imageIcon);
            c.gridwidth = 2;
            c.ipady = 0;
            c.gridx = 0;
            c.gridy = 1;
            frame.add(iconLabel, c);

            // Category
            JLabel categoryLabel = new JLabel("Category: ");
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            c.gridwidth = 1;
            c.ipadx = 20;
            c.ipady = 20;
            c.gridx = 0;
            c.gridy = 2;
            frame.add(categoryLabel, c);

            JLabel category = new JLabel(recipe.getCategory());
            category.setFont(new Font("Arial", Font.PLAIN, 14));
            c.gridwidth = 1;
            c.ipady = 20;
            c.gridx = 1;
            c.gridy = 2;
            frame.add(category, c);

            // Alcoholic
            JLabel alcoholicLabel = new JLabel("Alcoholic: ");
            alcoholicLabel.setFont(new Font("Arial", Font.BOLD, 14));
            c.gridwidth = 1;
            c.ipadx = 20;
            c.ipady = 20;
            c.gridx = 0;
            c.gridy = 3;
            frame.add(alcoholicLabel, c);

            JLabel alcoholic = new JLabel(String.valueOf(recipe.isAlcoholic()));
            alcoholic.setFont(new Font("Arial", Font.PLAIN, 14));
            c.gridwidth = 1;
            c.ipady = 20;
            c.gridx = 1;
            c.gridy = 3;
            frame.add(alcoholic, c);

            // Ingredients
            JLabel ingredientLabel = new JLabel("Ingredient(s): ");
            ingredientLabel.setFont(new Font("Arial", Font.BOLD, 14));
            c.gridwidth = 1;
            c.ipadx = 20;
            c.ipady = 20;
            c.gridx = 0;
            c.gridy = 4;
            frame.add(ingredientLabel, c);

            JLabel ingredient = new JLabel(recipe.getIngredientOutput());
            ingredient.setFont(new Font("Arial", Font.PLAIN, 14));
            c.gridwidth = 1;
            c.ipady = 20;
            c.gridx = 1;
            c.gridy = 4;
            frame.add(ingredient, c);

            // Category
            JLabel instructionLabel = new JLabel("Instructions: ");
            instructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
            c.gridwidth = 1;
            c.ipadx = 20;
            c.ipady = 20;
            c.gridx = 0;
            c.gridy = 5;
            frame.add(instructionLabel, c);

            JLabel instruction = new JLabel(recipe.getInstructionOutput());
            instruction.setFont(new Font("Arial", Font.PLAIN, 14));
            c.gridwidth = 1;
            c.ipady = 20;
            c.gridx = 1;
            c.gridy = 5;
            frame.add(instruction, c);

            frame.toFront();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Unable to generate JFrame", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
