package com.example.ahmed.diemensa.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ahmed.diemensa.Dish;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class SharedPreference {

    private static final String PREFS_NAME = "PRODUCT_APP";
    private static final String FAVORITES = "Product_Favorite";

    SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    private void saveFavorites(Context context, List<Dish> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }
    void addFavorite(Context context, Dish product) {
        List<Dish> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Dish>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    void removeFavorite(Context context, Dish product) {
        ArrayList<Dish> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);

            saveFavorites(context, favorites);
        }
    }

    ArrayList<Dish> getFavorites(Context context) {
        SharedPreferences preferences;
        List<Dish> favorites;

        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (preferences.contains(FAVORITES)) {
            String jsonFavorites = preferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            Dish[] favoriteItems = gson.fromJson(jsonFavorites,
                    Dish[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Dish>(favorites);
        } else
            return null;

        return (ArrayList<Dish>) favorites;
    }
}