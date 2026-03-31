package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.ui.Customer.MenuActivity;

public class ThankYouActivity extends AppCompatActivity {

    Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        btnYes = findViewById(R.id.btnReceiptYes);
        btnNo = findViewById(R.id.btnReceiptNo);

        btnYes.setOnClickListener(v -> finishOrder("Receipt will be sent"));
        btnNo.setOnClickListener(v -> finishOrder("No receipt"));
    }

    private void finishOrder(String msg) {
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();
        // Empty Payment Summary
        if (!AppData.orders.isEmpty()) {
            AppData.orders.remove(AppData.orders.size() - 1);
        }

        // Go back to menu
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}