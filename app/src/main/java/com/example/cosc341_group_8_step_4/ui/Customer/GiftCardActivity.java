package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;

public class GiftCardActivity extends AppCompatActivity {

    private EditText etCode;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);

        etCode = findViewById(R.id.etGiftCode);
        btnPay = findViewById(R.id.btnPayGift);

        double total = getIntent().getDoubleExtra("total", 0);

        btnPay.setOnClickListener(v -> {
            String code = etCode.getText().toString().trim();

            // Invalid card
            if (!AppData.giftCards.containsKey(code)) {
                new AlertDialog.Builder(this)
                        .setTitle("Invalid Card")
                        .setMessage("Gift card not found. Please try again.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            double balance = AppData.giftCards.get(code);

            // Case 1: Not enough balance (partial payment)
            if (balance < total) {
                double remaining = total - balance;

                // Deduct full balance
                AppData.giftCards.put(code, 0.0);

                new AlertDialog.Builder(this)
                        .setTitle("Partial Payment Applied")
                        .setMessage("Gift card used: $" + balance +
                                "\nRemaining amount: $" + remaining)
                        .setPositiveButton("OK", (dialog, which) -> {

                            // Return to payment method with remaining amount
                            Intent intent = new Intent(GiftCardActivity.this, PaymentMethodActivity.class);
                            intent.putExtra("total", remaining);
                            startActivity(intent);
                            finish();
                        })
                        .show();

                return;
            }

            // Case 2: Enough balance
            double newBalance = balance - total;
            AppData.giftCards.put(code, newBalance);

            new AlertDialog.Builder(this)
                    .setTitle("Payment Successful")
                    .setMessage("Remaining gift card balance: $" + newBalance)
                    .setPositiveButton("OK", (dialog, which) -> goToThankYou())
                    .show();
        });
    }

    private void goToThankYou() {
        startActivity(new Intent(this, ThankYouActivity.class));
        finish();
    }
}