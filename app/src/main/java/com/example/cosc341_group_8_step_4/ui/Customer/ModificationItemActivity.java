package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

import java.util.ArrayList;

public class ModificationItemActivity extends AppCompatActivity {

    private TextView tvDishName;
    private CheckBox cbOption1, cbOption2, cbOption3, cbOption4, cbOption5, cbOption6;
    private Button btnBack, btnAdditionalRequirement;

    private String itemName;
    private double itemPrice;
    private String[] itemOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_item);

        tvDishName = findViewById(R.id.tvDishName);

        cbOption1 = findViewById(R.id.cbOption1);
        cbOption2 = findViewById(R.id.cbOption2);
        cbOption3 = findViewById(R.id.cbOption3);
        cbOption4 = findViewById(R.id.cbOption4);
        cbOption5 = findViewById(R.id.cbOption5);
        cbOption6 = findViewById(R.id.cbOption6);

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

            if (cbOption1.getVisibility() == CheckBox.VISIBLE && cbOption1.isChecked()) {
                selectedOptions.add(cbOption1.getText().toString());
            }
            if (cbOption2.getVisibility() == CheckBox.VISIBLE && cbOption2.isChecked()) {
                selectedOptions.add(cbOption2.getText().toString());
            }
            if (cbOption3.getVisibility() == CheckBox.VISIBLE && cbOption3.isChecked()) {
                selectedOptions.add(cbOption3.getText().toString());
            }
            if (cbOption4.getVisibility() == CheckBox.VISIBLE && cbOption4.isChecked()) {
                selectedOptions.add(cbOption4.getText().toString());
            }
            if (cbOption5.getVisibility() == CheckBox.VISIBLE && cbOption5.isChecked()) {
                selectedOptions.add(cbOption5.getText().toString());
            }
            if (cbOption6.getVisibility() == CheckBox.VISIBLE && cbOption6.isChecked()) {
                selectedOptions.add(cbOption6.getText().toString());
            }

            Intent intent = new Intent(ModificationItemActivity.this, AdditionalRequirementActivity.class);
            intent.putExtra("itemName", itemName);
            intent.putExtra("itemPrice", itemPrice);
            intent.putStringArrayListExtra("selectedOptions", selectedOptions);
            startActivity(intent);
        });
    }

    private void setCheckboxOptions(String[] options) {
        CheckBox[] checkBoxes = {cbOption1, cbOption2, cbOption3, cbOption4, cbOption5, cbOption6};

        for (int i = 0; i < checkBoxes.length; i++) {
            if (options != null && i < options.length) {
                checkBoxes[i].setText(options[i]);
                checkBoxes[i].setVisibility(CheckBox.VISIBLE);
            } else {
                checkBoxes[i].setVisibility(CheckBox.GONE);
            }
        }
    }
}