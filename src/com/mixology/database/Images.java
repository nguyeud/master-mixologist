package com.mixology.database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Images {

    public static void loadImages() throws FileNotFoundException, InterruptedException {

//Change this next line to the path needed
        String drinksFilePath = "data\\DrinksMasterList";

        File list = new File(drinksFilePath);
        Scanner reader = new Scanner(list);

        //  Reading 23 images, The name of the drink, a picture of the drink, the ingredients, and instructions
        // The drink data is in the data file called DrinksMasterList
        for (int i = 0; i < 22; i++) {
            String drinkTitle = reader.nextLine();
            String drinklURL = reader.nextLine();
            String drinkSetText = "<HTML><body BGCOLOR=GREEN TEXT=WHITE STYLE=TEXT-ALIGN:center>";
            String drinkSetHeader = "<H1>" + drinkTitle + "</H1>";
            String drinkIngredients = reader.nextLine();
            String drinkInstructions = reader.nextLine();

            //Load the image
            Image image = null;
            try {
                URL url1 = new URL(drinklURL);
                image = ImageIO.read(url1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Create a JFrame with title "The Drinks Name" and sets the frame to full screen
            JFrame frame = new JFrame();
            frame.setTitle(drinkTitle);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            //Create a JLabel and add all the information to it.
            // The name of the drink, a picture of the drink, the ingredients, and instructions
            JLabel label = new JLabel(new ImageIcon(image));
            label.setText(drinkSetText + drinkSetHeader + drinkIngredients + drinkInstructions);

            //Add the JLabel to the JFrame and display it
            frame.add(label);
            frame.setVisible(true);

            //The timer between drink images is set to 5 seconds
            Thread.sleep(5000);

            //Hit the x on the corner of the screen to close...
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }


    //This just drives the Images class...  Take it out and call from our driver program
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        loadImages();
    }
}