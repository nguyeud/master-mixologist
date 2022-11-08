package com.mixology.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class SearchRequest {
    // Fields
    private static final String urlString = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";

    // Methods
    /*
     * Method to fetch data from the server
     */
    public static Map<String, String> sendRequest(String name) {
        Map<String, String> map = new TreeMap<>();

        try {
            URL url = new URL(urlString + name);

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

                // Iterate over array and add to the results List if results != null
                if (dataArr != null) {
                    for (int i = 0; i < dataArr.size(); i++) {
                        JSONObject newObj = (JSONObject) dataArr.get(i);

                        map.put((String) newObj.get("idDrink"), (String) newObj.get("strDrink"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void showCocktail(String input, Map<String, String> cocktails) {
        System.out.println("SEARCH RESULTS FOR: " + input.toUpperCase());

        System.out.println("===================");

        List<Integer> cocktailLength = new ArrayList<>();
        for (String cocktail : cocktails.values()) {
            cocktailLength.add(cocktail.length());
        }

        int maxLength = Collections.max(cocktailLength);

        System.out.printf("%-8s | %-10s\n", "ID", "Cocktail");
        String border = "=";
        System.out.printf("=========|%s\n", border.repeat(maxLength));

        for (Map.Entry<String, String> entry : cocktails.entrySet()) {
            System.out.printf("%-8s | %-10s\n", entry.getKey(), entry.getValue());
        }
    }
}
