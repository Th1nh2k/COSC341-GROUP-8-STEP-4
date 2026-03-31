package com.example.cosc341_group_8_step_4.ui.Staff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

public class StaffDashboardActivity extends AppCompatActivity {

    private Button btnManageMenu, btnViewOrders, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        btnManageMenu = findViewById(R.id.btnTask2ManageMenu);
        btnViewOrders = findViewById(R.id.btnTask5ViewOrders);
        btnLogout = findViewById(R.id.btnLogout);

        btnManageMenu.setOnClickListener(v -> {
            Intent intent = new Intent(StaffDashboardActivity.this, ManageMenuActivity.class);
            startActivity(intent);
        });
//
//        btnViewOrders.setOnClickListener(v -> {
//            Intent intent = new Intent(StaffDashboardActivity.this, ViewOrdersActivity.class);
//            startActivity(intent);
//        });

        btnLogout.setOnClickListener(v -> finish());
    }
}