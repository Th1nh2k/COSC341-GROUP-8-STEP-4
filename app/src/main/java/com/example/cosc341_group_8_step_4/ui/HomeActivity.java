package com.example.cosc341_group_8_step_4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.data.MockMenuDataGenerator;
import com.example.cosc341_group_8_step_4.data.MockOrderDataGenerator;
import com.example.cosc341_group_8_step_4.ui.Customer.MenuActivity;
import com.example.cosc341_group_8_step_4.ui.Staff.StaffLoginActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnCustomer, btnStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Generate fake past orders once for Task 5
        if (AppData.orders.isEmpty()) {
            MockOrderDataGenerator.generateMockOrders();
        }
        if (AppData.menuItems.isEmpty()) {
            AppData.menuItems.addAll(MockMenuDataGenerator.getMenuItems());
        }

        btnCustomer = findViewById(R.id.btnCustomer);
        btnStaff = findViewById(R.id.btnStaff);

        btnCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        btnStaff.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, StaffLoginActivity.class);
            startActivity(intent);
        });
    }
}