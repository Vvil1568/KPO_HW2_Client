package com.vvi.restaurantapp.items;

public class Dish {
    private int id;
    private String name;
    private String description;
    private double price;
    private long time;
    private String image;

    public Dish(int id, String name, String description, double price, long time, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.time = time;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public long getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public void copy(Dish dish) {
        this.id = dish.id;
        this.name = dish.name;
        this.description = dish.description;
        this.time = dish.time;
        this.image = dish.image;
    }
}