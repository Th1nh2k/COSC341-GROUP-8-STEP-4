package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

import java.util.ArrayList;

public class ModificationItemActivity extends AppCompatActivity {

    private TextView tvDishName;
    private GridLayout layoutOptions;
    private Button btnBack, btnAdditionalRequirement;

    private String itemName;
    private double itemPrice;
    private String[] itemOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_item);

        tvDishName = findViewById(R.id.tvDishName);

        layoutOptions = findViewById(R.id.layoutOptions);

        btnBack = findViewById(R.id.btnBack);
        btnAdditionalRequirement = findViewById(R.id.btnAdditionalRequirement);

        itemName = getIntent().getStringExtra("itemName");
        itemPrice = getIntent().getDoubleExtra("itemPrice", 0.0);
        itemOptions = getIntent().getStringArrayExtra("itemOptions");

        if (itemName == null) {
            itemName = "Selected Dish";
        }

        tvDishName.setText(itemName);
        setCheckboxOptions(itemOptions);

        btnBack.setOnClickListener(v -> finish());

        btnAdditionalRequirement.setOnClickListener(v -> {
            ArrayList<String> selectedOptions = new ArrayList<>();

            for (int i = 0; i < layoutOptions.getChildCount(); i++) {
                View child = layoutOptions.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    if (checkBox.isChecked()) {
                        selectedOptions.add(checkBox.getText().toString());
                    }
                }
            }

            Intent intent = new Intent(ModificationItemActivity.this, AdditionalRequirementActivity.class);
            intent.putExtra("itemName", itemName);
            intent.putExtra("itemPrice", itemPrice);
            intent.putStringArrayListExtra("selectedOptions", selectedOptions);
            startActivity(intent);
        });
    }

    private void setCheckboxOptions(String[] options) {
        layoutOptions.removeAllViews();

        if (options == null) return;

        for (String option : options) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(option);
            checkBox.setTextSize(18);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(16, 16, 16, 16);
            checkBox.setLayoutParams(params);

            layoutOptions.addView(checkBox);
        }
    }
}