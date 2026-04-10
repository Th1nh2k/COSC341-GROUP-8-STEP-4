package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;

public class PaymentMethodActivity extends AppCompatActivity {

    private Button btnCash, btnGiftCard, btnCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        btnCash = findViewById(R.id.btnCash);
        btnGiftCard = findViewById(R.id.btnGiftCard);
        btnCard = findViewById(R.id.btnCard);

        double total = getIntent().getDoubleExtra("total", 0);

        btnCash.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Cash Payment")
                    .setMessage("A server is coming to collect your payment.")
                    .setPositiveButton("OK", (d, w) -> {
                        AppData.isServerCalled = true;
                        AppData.orders.clear();
                        startActivity(new Intent(this, MenuActivity.class));
                        finish();
                    })
                    .show();
        });

        btnGiftCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, GiftCardActivity.class);
            intent.putExtra("total", total);
            startActivity(intent);
        });

        btnCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CardPaymentActivity.class);
            intent.putExtra("total", total);
            startActivity(intent);
        });
    }
}