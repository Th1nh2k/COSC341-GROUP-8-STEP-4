package com.example.cosc341_group_8_step_4.model;

import java.util.List;

public class Order {
    public List<CartItem> items;
    public double total;
    public String submittedAt;

    public Order(List<CartItem> items, double total, String submittedAt) {
        this.items = items;
        this.total = total;
        this.submittedAt = submittedAt;
    }
}