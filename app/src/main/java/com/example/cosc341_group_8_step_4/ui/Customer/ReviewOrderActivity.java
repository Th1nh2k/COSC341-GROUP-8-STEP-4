package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.CartItem;
import com.example.cosc341_group_8_step_4.model.Order;
import com.example.cosc341_group_8_step_4.ui.Customer.OrderSummaryActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerReviewOrder;
    private TextView tvTotalPrice;
    private Button btnAddMore, btnSubmitOrder;
    private ReviewOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        recyclerReviewOrder = findViewById(R.id.recyclerReviewOrder);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnAddMore = findViewById(R.id.btnAddMoreDishes);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        adapter = new ReviewOrderAdapter(AppData.cart, this::updateTotalPrice);
        recyclerReviewOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerReviewOrder.setAdapter(adapter);

        updateTotalPrice();

        btnAddMore.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewOrderActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnSubmitOrder.setOnClickListener(v -> submitOrder());
    }

    private void updateTotalPrice() {
        double total = 0.0;

        for (CartItem item : AppData.cart) {
            total += item.price * item.quantity;
        }

        tvTotalPrice.setText(String.format(Locale.US, "Total: $%.2f", total));
    }

    private void submitOrder() {
        if (AppData.cart.isEmpty()) {
            tvTotalPrice.setText("Your cart is empty.Please select items from the menu");
            return;
        }

        double total = 0.0;
        List<CartItem> savedItems = new ArrayList<>();
        StringBuilder summaryBuilder = new StringBuilder();

        String submittedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                .format(new Date());

        summaryBuilder.append("Date: ").append(submittedAt).append("\n\n");

        for (CartItem item : AppData.cart) {
            total += item.price * item.quantity;

            savedItems.add(new CartItem(
                    item.name,
                    item.price,
                    item.selectedOptions == null ? new ArrayList<>() : new ArrayList<>(item.selectedOptions),
                    item.note,
                    item.quantity
            ));

            summaryBuilder.append(item.name)
                    .append(" x")
                    .append(item.quantity)
                    .append(" - $")
                    .append(String.format(Locale.US, "%.2f", item.price * item.quantity))
                    .append("\n");

            if (item.selectedOptions != null && !item.selectedOptions.isEmpty()) {
                summaryBuilder.append("Options: ")
                        .append(String.join(", ", item.selectedOptions))
                        .append("\n");
            }

            if (item.note != null && !item.note.isEmpty()) {
                summaryBuilder.append("Note: ")
                        .append(item.note)
                        .append("\n");
            }

            summaryBuilder.append("\n");
        }

        summaryBuilder.append("Total: $")
                .append(String.format(Locale.US, "%.2f", total));

        Order newOrder = new Order(savedItems, total, submittedAt);
        AppData.orders.add(newOrder);

        AppData.cart.clear();

        Intent intent = new Intent(ReviewOrderActivity.this, OrderSummaryActivity.class);
        intent.putExtra("orderSummary", summaryBuilder.toString());
        startActivity(intent);
        finish();
    }
}