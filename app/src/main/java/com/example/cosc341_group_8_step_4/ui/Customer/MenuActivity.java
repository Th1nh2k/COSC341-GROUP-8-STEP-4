package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerMenu;
    private MenuAdapter adapter;
    private List<MenuItem> menuList;

    private Button btnAll, btnFood, btnDrinks, btnDesserts;
    private Button btnCallServer, btnPayment, btnViewCart;

    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerMenu = findViewById(R.id.recyclerMenu);

        btnAll = findViewById(R.id.btnAll);
        btnFood = findViewById(R.id.btnFood);
        btnDrinks = findViewById(R.id.btnDrinks);
        btnDesserts = findViewById(R.id.btnDesserts);

        btnCallServer = findViewById(R.id.btnCallServer);
        btnPayment = findViewById(R.id.btnPayment);
        btnViewCart = findViewById(R.id.btnViewCart);

        menuList = AppData.menuItems;
        adapter = new MenuAdapter(menuList);

        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenu.setAdapter(adapter);

        btnAll.setOnClickListener(v -> {
            filterMenu("All");
            updateCategoryButtonStyles(btnAll);
        });

        btnFood.setOnClickListener(v -> {
            filterMenu("Food");
            updateCategoryButtonStyles(btnFood);
        });

        btnDrinks.setOnClickListener(v -> {
            filterMenu("Drinks");
            updateCategoryButtonStyles(btnDrinks);
        });

        btnDesserts.setOnClickListener(v -> {
            filterMenu("Desserts");
            updateCategoryButtonStyles(btnDesserts);
        });

        updateCategoryButtonStyles(btnFood);
        updateCallServerButton();

        btnCallServer.setOnClickListener(v -> {
            if (!AppData.isServerCalled) {
                showCallServerDialog();
            } else {
                showCancelCallServerDialog();
            }
        });

        btnPayment.setOnClickListener(v -> {
            if (AppData.orders.isEmpty()) {
                new AlertDialog.Builder(MenuActivity.this)
                        .setTitle("Payment")
                        .setMessage("No submitted order available for payment.")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                Intent intent = new Intent(MenuActivity.this, PaymentSummaryActivity.class);
                startActivity(intent);
            }
        });

        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ReviewOrderActivity.class);
            startActivity(intent);
        });
    }

    private void showCallServerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Call Server")
                .setMessage("Do you want to call the server?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    AppData.isServerCalled = true;
                    updateCallServerButton();
                    Toast.makeText(this, "Server has been called", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showCancelCallServerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Call Server")
                .setMessage("Do you want to cancel the server call?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    AppData.isServerCalled = false;
                    updateCallServerButton();
                    Toast.makeText(this, "Server call cancelled", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateCallServerButton() {
        if (AppData.isServerCalled) {
            btnCallServer.setText("Cancel Call Server");
            btnCallServer.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#757575"))
            );
        } else {
            btnCallServer.setText("Call Server");
            btnCallServer.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#D32F2F"))
            );
        }
    }

    private void updateCategoryButtonStyles(Button selectedButton) {
        Button[] buttons = {btnAll, btnFood, btnDrinks, btnDesserts};

        for (Button button : buttons) {
            button.setTextColor(Color.parseColor("#2E7D32"));
            button.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            );
        }

        selectedButton.setTextColor(Color.parseColor("#FFFFFF"));
        selectedButton.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#2E7D32"))
        );
    }

    private void filterMenu(String category) {
        currentCategory = category;
        List<MenuItem> filteredList = new ArrayList<>();

        if (category.equals("All")) {
            filteredList.addAll(AppData.menuItems);
        } else {
            for (MenuItem item : AppData.menuItems) {
                if (item.category.equalsIgnoreCase(category)) {
                    filteredList.add(item);
                }
            }
        }

        adapter = new MenuAdapter(filteredList);
        recyclerMenu.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterMenu(currentCategory);
        updateCategoryButtonStyles(
                currentCategory.equals("All") ? btnAll :
                        currentCategory.equals("Food") ? btnFood :
                                currentCategory.equals("Drinks") ? btnDrinks : btnDesserts
        );
    }
}