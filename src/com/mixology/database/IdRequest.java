package com.mixology.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IdRequest {
    // Fields
    private static final String urlString = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";

    // Methods
    /*
     * Method to fetch data from the server
     */
    public static Recipe sendRequest(int id) {
        Recipe recipe = null;

        try {
            URL url = new URL(urlString + id);

            // Open GET request to URL
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.connect();

            // Getting the response code
            int responseCode = request.getResponseCode();

            // If response code != 200, then not successful, throw exception
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());

                // Map to JSON objects
                JSONObject dataObj = (JSONObject) new JSONParser().parse(inputStreamReader);

                // Get the required object from the above create object
                // In the API, "drinks" is the required object (this is the same among all the filters)
                JSONArray dataArr = (JSONArray) dataObj.get("drinks");
                JSONObject newObj = (JSONObject) dataArr.get(0);

                // Create new Recipe object
                String rId = (String) newObj.get("idDrink");
                String rName = (String) newObj.get("strDrink");
                String rCategory = (String) newObj.get("strCategory");
                String rInstructions = (String) newObj.get("strInstructions");
                boolean rIsAlcoholic = ((String) newObj.get("strAlcoholic")).equals("Alcoholic");
                String rImage = (String) newObj.get("strDrinkThumb");

                // Iterate over ingredient to add to a list for Recipe class
                List<String> rIngredients = new ArrayList<>();
                for (int i = 1; i <= 15; i++) {
                    String ingredient = (String) newObj.get("strIngredient" + i);
                    String amount = (String) newObj.get("strMeasure" + i);

                    if (ingredient != null) {
                        rIngredients.add(amount + " - " + ingredient);
                    }
                }

                recipe = new Recipe(rId, rName, rCategory, rIngredients, rInstructions, rIsAlcoholic, rImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipe;
    }
}
