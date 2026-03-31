package com.example.cosc341_group_8_step_4.model;

public class MenuItem {
    public String name;
    public String description;
    public double price;
    public String category;
    public int imageResId;
    public String imagePath;
    public String[] options;

    public MenuItem(String name, String description, double price, String category, int imageResId, String[] options) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageResId = imageResId;
        this.imagePath = null;
        this.options = options;
    }

    public MenuItem(String name, String description, double price, String category, int imageResId, String imagePath, String[] options) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageResId = imageResId;
        this.imagePath = imagePath;
        this.options = options;
    }
}