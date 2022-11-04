package com.mixology.database;

import java.util.Map;

class SearchRequestTest {

    public static void main(String[] args) {
        Map<String, String> margarita = SearchRequest.sendRequest("margarita");
        System.out.println(margarita);

        Map<String, String> longIsland = SearchRequest.sendRequest("long island");
        System.out.println(longIsland);
    }
}
