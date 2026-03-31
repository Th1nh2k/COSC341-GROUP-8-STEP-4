package com.example.cosc341_group_8_step_4.model;

import java.util.ArrayList;

public class CartItem {
    public String name;
    public double price;
    public ArrayList<String> selectedOptions;
    public String note;
    public int quantity;

    public CartItem(String name, double price, ArrayList<String> selectedOptions, String note, int quantity) {
        this.name = name;
        this.price = price;
        this.selectedOptions = selectedOptions;
        this.note = note;
        this.quantity = quantity;
    }
}