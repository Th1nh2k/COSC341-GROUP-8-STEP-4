package com.example.cosc341_group_8_step_4.data;


import com.example.cosc341_group_8_step_4.model.CartItem;
import com.example.cosc341_group_8_step_4.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//FAKE DATA FOR TASK 5
public class MockOrderDataGenerator {

    public static void generateMockOrders() {
        Random random = new Random();

        String[] foodNames = {
                "Fried Rice", "Noodles", "Milk Tea", "Cheesecake",
                "Burger", "Ice Cream", "Spring Rolls", "Lemon Tea"
        };

        String[][] possibleOptions = {
                {"No Onion", "Extra Egg"},
                {"Spicy", "Less Salt"},
                {"Less Sugar", "No Ice"},
                {"Extra Sauce", "No Sauce"},
                {"Extra Cheese", "No Pickles"}
        };

        for (int i = 0; i < 5; i++) {  // create 5 fake orders

            List<CartItem> items = new ArrayList<>();
            double total = 0;

            int itemCount = 1 + random.nextInt(3); // 1–3 items per order

            for (int j = 0; j < itemCount; j++) {

                String name = foodNames[random.nextInt(foodNames.length)];
                double price = 5 + random.nextInt(10); // random price

                ArrayList<String> options = new ArrayList<>();
                String[] opt = possibleOptions[random.nextInt(possibleOptions.length)];
                for (String o : opt) {
                    if (random.nextBoolean()) {
                        options.add(o);
                    }
                }

                String note = random.nextBoolean() ? "Less spicy" : "";

                CartItem item = new CartItem(name, price, options, note, 1);
                items.add(item);

                total += price;
            }

            // random date (within last few days)
            long now = System.currentTimeMillis();
            long randomPast = now - (random.nextInt(5) * 24L * 60 * 60 * 1000);

            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                    .format(new Date(randomPast));

            Order order = new Order(items, total, date);
            AppData.orders.add(order);
        }
    }
}