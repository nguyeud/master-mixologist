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

class ApiFilterRequest {
    // Categories: https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list
    // Ingredients: https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list
    // Alcoholic: https://www.thecocktaildb.com/api/json/v1/1/list.php?a=list

    private String urlString;

    public ApiFilterRequest(String urlString) {
        this.urlString = urlString;
    }

    /*
     * Method to fetch data from the server
     */
    private static List<Object> sendRequest(String urlString) {
        List<Object> result = new ArrayList<>();

        try {
            URL url = new URL(urlString);

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

                // Get filter type
                int index = urlString.indexOf("?");
                String code = urlString.substring(index + 1, index + 2);
                String filter;

                switch (code) {
                    case "c":
                        filter = "strCategory";
                        break;
                    case "i":
                        filter = "strIngredient1";
                        break;
                    case "a":
                        filter = "strAlcoholic";
                        break;
                    default:
                        filter = "";
                        break;
                }

                // Iterate over array and add to the results List
                for (int i = 0; i < dataArr.size(); i++) {
                    JSONObject new_obj = (JSONObject) dataArr.get(i);

                    result.add(new_obj.get(filter));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Result: " + result);

        return result;
    }

    public String getUrlString() {
        return urlString;
    }
}
