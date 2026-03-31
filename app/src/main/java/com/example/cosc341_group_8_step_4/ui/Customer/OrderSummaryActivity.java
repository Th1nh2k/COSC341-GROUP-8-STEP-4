package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView tvOrderSummary;
    private Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        tvOrderSummary = findViewById(R.id.tvOrderSummary);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        String summaryText = getIntent().getStringExtra("orderSummary");
        if (summaryText == null) {
            summaryText = "No order information available.";
        }

        tvOrderSummary.setText(summaryText);

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSummaryActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}