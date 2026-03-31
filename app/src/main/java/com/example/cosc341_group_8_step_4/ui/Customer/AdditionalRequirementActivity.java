package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.CartItem;

import java.util.ArrayList;

public class AdditionalRequirementActivity extends AppCompatActivity {

    private TextView tvDishName;
    private TextView tvSelectedOptions;
    private EditText etRequirement;
    private Button btnBack, btnAddToCart;

    private String itemName;
    private double itemPrice;
    private ArrayList<String> selectedOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_requirement);

        tvDishName = findViewById(R.id.tvDishNameRequirement);
        tvSelectedOptions = findViewById(R.id.tvSelectedOptions);
        etRequirement = findViewById(R.id.etRequirement);
        btnBack = findViewById(R.id.btnBackRequirement);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        itemName = getIntent().getStringExtra("itemName");
        itemPrice = getIntent().getDoubleExtra("itemPrice", 0.0);
        selectedOptions = getIntent().getStringArrayListExtra("selectedOptions");

        if (itemName == null) itemName = "Selected Dish";
        if (selectedOptions == null) selectedOptions = new ArrayList<>();

        tvDishName.setText(itemName);

        if (selectedOptions.isEmpty()) {
            tvSelectedOptions.setText("Selected options: None");
        } else {
            tvSelectedOptions.setText("Selected options: " + String.join(", ", selectedOptions));
        }

        btnBack.setOnClickListener(v -> finish());

        btnAddToCart.setOnClickListener(v -> {
            String note = etRequirement.getText().toString().trim();

            CartItem cartItem = new CartItem(
                    itemName,
                    itemPrice,
                    selectedOptions,
                    note,
                    1
            );

            AppData.cart.add(cartItem);

            Intent intent = new Intent(AdditionalRequirementActivity.this, ReviewOrderActivity.class);
            startActivity(intent);
        });
    }
}