package com.example.cosc341_group_8_step_4.data;

import com.example.cosc341_group_8_step_4.model.CartItem;
import com.example.cosc341_group_8_step_4.model.MenuItem;
import com.example.cosc341_group_8_step_4.model.Order;

import java.util.ArrayList;
import java.util.List;

public class AppData {

    // ====== "DATABASE TABLES" ======
    public static List<MenuItem> menuItems = new ArrayList<>();
    public static List<CartItem> cart = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static java.util.HashMap<String, Double> giftCards = new java.util.HashMap<>();

    // Gift Card Data
    static {
        giftCards.put("12345", 50.0);
        giftCards.put("54321", 20.0);
        giftCards.put("99999", 100.0);
    }
    // ====== MENU OPERATIONS ======
    public static void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public static void updateMenuItem(int index, MenuItem updatedItem) {
        if (index >= 0 && index < menuItems.size()) {
            menuItems.set(index, updatedItem);
        }
    }

    public static void deleteMenuItem(int index) {
        if (index >= 0 && index < menuItems.size()) {
            menuItems.remove(index);
        }
    }

    // ====== CART OPERATIONS ======
    public static void addToCart(CartItem item) {
        cart.add(item);
    }

    public static void clearCart() {
        cart.clear();
    }

    // ====== ORDER OPERATIONS ======
    public static void addOrder(Order order) {
        orders.add(order);
    }
}