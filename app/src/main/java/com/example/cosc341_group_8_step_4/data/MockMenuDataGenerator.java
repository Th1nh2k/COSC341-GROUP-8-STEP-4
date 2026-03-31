package com.example.cosc341_group_8_step_4.data;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MockMenuDataGenerator {

    public static List<MenuItem> getMenuItems() {
        List<MenuItem> list = new ArrayList<>();

        list.add(new MenuItem(
                "Fried Rice",
                "Delicious rice",
                12.99,
                "Food",
                R.drawable.fried_rice,
                new String[]{"No Onion", "Extra Egg", "Less Salt", "Spicy", "No Peas", "Extra Sauce"}
        ));

        list.add(new MenuItem(
                "Noodles",
                "Spicy noodles",
                10.99,
                "Food",
                R.drawable.noodles,
                new String[]{"Mild", "Medium", "Extra Spicy", "Extra Noodles", "No Garlic", "Extra Sauce"}
        ));

        list.add(new MenuItem(
                "Milk Tea",
                "Sweet drink",
                5.99,
                "Drinks",
                R.drawable.milk_tea,
                new String[]{"Less Sugar", "No Sugar", "More Ice", "No Ice", "Pearls", "Large Size"}
        ));

        list.add(new MenuItem(
                "Cheesecake",
                "Creamy dessert",
                6.49,
                "Desserts",
                R.drawable.cheesecake,
                new String[]{"Extra Cream", "Strawberry Sauce", "Chocolate Sauce", "No Sauce", "Add Ice Cream", "Take Away"}
        ));

        return list;
    }
}