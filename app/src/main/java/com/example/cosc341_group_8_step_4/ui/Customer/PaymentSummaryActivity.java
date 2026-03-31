package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.CartItem;
import com.example.cosc341_group_8_step_4.model.Order;

import java.util.Locale;

public class PaymentSummaryActivity extends AppCompatActivity {

    private static final double TAX_RATE = 0.13;

    private TextView tvItemsSummary, tvSubtotal, tvTax, tvTotal;
    private Button btnBackToMenu, btnContinueToPay;

    private double finalTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        tvItemsSummary = findViewById(R.id.tvItemsSummary);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTax = findViewById(R.id.tvTax);
        tvTotal = findViewById(R.id.tvTotal);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnContinueToPay = findViewById(R.id.btnContinueToPay);

        loadLatestSubmittedOrder();

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSummaryActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnContinueToPay.setOnClickListener(v -> {
            if (AppData.orders.isEmpty()) {
                Toast.makeText(this, "No submitted order available", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(PaymentSummaryActivity.this, PaymentMethodActivity.class);
            intent.putExtra("total", finalTotal);
            startActivity(intent);
        });
    }

    private void loadLatestSubmittedOrder() {
        if (AppData.orders.isEmpty()) {
            tvItemsSummary.setText("No submitted order available.");
            tvSubtotal.setText("Subtotal: $0.00");
            tvTax.setText("Tax (13%): $0.00");
            tvTotal.setText("Total: $0.00");
            btnContinueToPay.setEnabled(false);
            return;
        }

        Order latestOrder = AppData.orders.get(AppData.orders.size() - 1);

        StringBuilder summaryBuilder = new StringBuilder();
        double subtotal = 0.0;

        if (latestOrder.items != null) {
            for (CartItem item : latestOrder.items) {
                double itemTotal = item.price * item.quantity;
                subtotal += itemTotal;

                summaryBuilder.append(item.name)
                        .append(" x")
                        .append(item.quantity)
                        .append(" - $")
                        .append(String.format(Locale.US, "%.2f", itemTotal))
                        .append("\n");

                if (item.selectedOptions != null && !item.selectedOptions.isEmpty()) {
                    summaryBuilder.append("Options: ")
                            .append(String.join(", ", item.selectedOptions))
                            .append("\n");
                }

                if (item.note != null && !item.note.trim().isEmpty()) {
                    summaryBuilder.append("Note: ")
                            .append(item.note)
                            .append("\n");
                }

                summaryBuilder.append("\n");
            }
        }

        double tax = subtotal * TAX_RATE;
        finalTotal = subtotal + tax;

        tvItemsSummary.setText(summaryBuilder.toString().trim());
        tvSubtotal.setText(String.format(Locale.US, "Subtotal: $%.2f", subtotal));
        tvTax.setText(String.format(Locale.US, "Tax (13%%): $%.2f", tax));
        tvTotal.setText(String.format(Locale.US, "Total: $%.2f", finalTotal));
    }
}