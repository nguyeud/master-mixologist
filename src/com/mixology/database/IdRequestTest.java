package com.mixology.database;

import java.util.ArrayList;
import java.util.Map;

class IdRequestTest {

    public static void main(String[] args) {
        Recipe margarita = IdRequest.sendRequest(12322);       // strawberry margarita
        System.out.println(margarita);

        Recipe longIsland = IdRequest.sendRequest(11002);       // long island tea
        System.out.println(longIsland);
    }
}
