package com.vvi.restaurantapp.util;

import com.vvi.restaurantapp.items.Comment;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.items.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;

public class SerializationUtils {
    public static Dish deserializeDish(JSONObject object) {
        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            String desc = object.getString("desc");
            double price = object.getDouble("price");
            long time = object.getLong("time");
            String image = object.getString("image");
            return new Dish(id, name, desc, price, time, image);
        } catch (JSONException e) {
            return null;
        }
    }

    public static ArrayList<Dish> deserializeDishList(JSONObject object) {
        try {
            ArrayList<Dish> dishes = new ArrayList<>();
            JSONArray dishesObj = object.getJSONArray("dishes");
            for (int i = 0; i < dishesObj.length(); i++) {
                Dish dish = SerializationUtils.deserializeDish(dishesObj.getJSONObject(i));
                if (dish != null) {
                    dishes.add(dish);
                }
            }
            return dishes;
        } catch (JSONException e) {
            return null;
        }
    }

    public static User deserializeUser(JSONObject object) {
        try {
            String login = object.getString("login");
            String fio = object.getString("fio");
            boolean isAdmin = object.getBoolean("isAdmin");
            return new User(fio, login, isAdmin);
        } catch (JSONException e) {
            return null;
        }
    }

    public static Comment deserializeComment(JSONObject object) {
        try {
            String name = object.getString("name");
            String comment = object.getString("body");
            int stars = object.getInt("stars");
            return new Comment(name, stars, comment);
        } catch (JSONException e) {
            return null;
        }
    }

    public static ArrayList<Comment> deserializeCommentList(JSONObject object) {
        try {
            ArrayList<Comment> comments = new ArrayList<>();
            JSONArray commentsObj = object.getJSONArray("comments");
            for (int i = 0; i < commentsObj.length(); i++) {
                Comment comment = SerializationUtils.deserializeComment(commentsObj.getJSONObject(i));
                if (comment != null) {
                    comments.add(comment);
                }
            }
            return comments;
        } catch (JSONException e) {
            return null;
        }
    }

    public static ArrayList<AbstractMap.SimpleEntry<String, Double>> deserializeRatingList(JSONObject object) {
        try {
            ArrayList<AbstractMap.SimpleEntry<String, Double>> ratings = new ArrayList<>();
            JSONArray ratingsObj = object.getJSONArray("ratings");
            for (int i = 0; i < ratingsObj.length(); i++) {
                JSONObject ratingObj = ratingsObj.getJSONObject(i);
                String name = ratingObj.getString("name");
                double rating = ratingObj.getDouble("rating");
                ratings.add(new AbstractMap.SimpleEntry<>(name, rating));
            }
            return ratings;
        } catch (JSONException e) {
            return null;
        }
    }
}
