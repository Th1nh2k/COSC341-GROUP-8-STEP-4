package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

public class CardPaymentActivity extends AppCompatActivity {

    private EditText etCardNumber, etCVV, etExpiry, etCardName;
    private Button btnPayCard, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        // Bind views
        etCardNumber = findViewById(R.id.etCardNumber);
        etCVV = findViewById(R.id.etCVV);
        etExpiry = findViewById(R.id.etExpiry);
        etCardName = findViewById(R.id.etCardName);
        btnPayCard = findViewById(R.id.btnPayCard);
        btnBack = findViewById(R.id.btnBack);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Pay button
        btnPayCard.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {

        String cardNumber = etCardNumber.getText().toString().trim();
        String cvv = etCVV.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        String name = etCardName.getText().toString().trim();

        // ===== VALIDATION =====

        if (cardNumber.length() != 16) {
            etCardNumber.setError("Card number must be 16 digits");
            return;
        }

        if (cvv.length() != 3) {
            etCVV.setError("CVV must be 3 digits");
            return;
        }

        if (expiry.length() != 4) {
            etExpiry.setError("Expiry must be 4 digits (MMYY)");
            return;
        }

        if (name.isEmpty()) {
            etCardName.setError("Cardholder name required");
            return;
        }

        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent);
        finish();
    }
}