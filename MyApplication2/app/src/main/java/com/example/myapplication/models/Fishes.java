package com.example.myapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Fishes {
    private List<Fish> fishes;
    public Fishes(JSONArray jsonArray) {
        parseJson(jsonArray);
    }
    private void parseJson(JSONArray jsonArray) {
        fishes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fishes.add(new Fish(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Fish> getFishes(){
        return fishes;
    }
}
