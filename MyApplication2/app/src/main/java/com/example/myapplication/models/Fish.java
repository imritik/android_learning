package com.example.myapplication.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Fish {
    String fish_name,cat_name,fish_img,size_name,price;

    public Fish(JSONObject jsonObject) {
        parseJson(jsonObject);
    }
    private void parseJson(JSONObject jsonObject) {
        try {
            fish_name = jsonObject.getString("fish_name");
            cat_name = jsonObject.getString("cat_name");
            fish_img = jsonObject.getString("fish_img");
            size_name = jsonObject.getString("size_name");
            price=jsonObject.getString("price");
        } catch ( JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFish_name() {
        return fish_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getFish_img() {
        return fish_img;
    }

    public String getSize_name() {
        return size_name;
    }

    public String  getPrice() {
        return price;
    }
}
