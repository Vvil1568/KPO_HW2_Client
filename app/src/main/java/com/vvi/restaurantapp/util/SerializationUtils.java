package com.vvi.restaurantapp.util;

import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.items.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SerializationUtils {
    public static Dish deserializeDish(JSONObject object){
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

    public static ArrayList<Dish> deserializeDishList(JSONObject object){
        try {
            ArrayList<Dish> dishes = new ArrayList<>();
            JSONArray dishesObj = object.getJSONArray("dishes");
            for(int i=0;i<dishesObj.length();i++){
                Dish dish = SerializationUtils.deserializeDish(dishesObj.getJSONObject(i));
                if(dish!=null) {
                    dishes.add(dish);
                }
            }
            return dishes;
        }catch (JSONException e){
            return null;
        }
    }

    public static User deserializeUser(JSONObject object){
        try {
            String login = object.getString("login");
            String fio = object.getString("fio");
            boolean isAdmin = object.getBoolean("isAdmin");
            return new User(fio, login, isAdmin);
        }catch (JSONException e){
            return null;
        }
    }
}
