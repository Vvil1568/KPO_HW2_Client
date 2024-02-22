package com.vvi.restaurantapp.util;

import com.vvi.restaurantapp.items.Dish;

import org.json.JSONException;
import org.json.JSONObject;

public class SerializationUtils {
    public static Dish deserialize(JSONObject object){
        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            String desc = object.getString("desc");
            double price = object.getDouble("price");
            long time = object.getLong("time");
            return new Dish(id, name, desc, price, time);
        }catch (JSONException e){
            return null;
        }
    }
}
