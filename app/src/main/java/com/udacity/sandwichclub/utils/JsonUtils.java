package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {


        Sandwich sandwich = null;
        try {
            JSONObject sandwichJsonObjectroot = new JSONObject(json);

            JSONObject name = sandwichJsonObjectroot.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = jsonArrayToStringList(alsoKnownAsJsonArray);

            String placeOfOrigin = sandwichJsonObjectroot.getString("placeOfOrigin");
            String description = sandwichJsonObjectroot.getString("description");
            String image = sandwichJsonObjectroot.getString("image");

            JSONArray ingredientsJsonArray = sandwichJsonObjectroot.getJSONArray("ingredients");
            List<String> ingredients = jsonArrayToStringList(ingredientsJsonArray);

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;

    }

    private static List<String> jsonArrayToStringList(JSONArray jsonArray) throws JSONException {
        int arrayLength = jsonArray.length();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arrayLength; i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }
}

