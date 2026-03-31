package com.example.cosc341_group_8_step_4.ui.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.model.CartItem;

import java.util.List;
import java.util.Locale;

public class ReviewOrderAdapter extends RecyclerView.Adapter<ReviewOrderAdapter.ViewHolder> {

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    private final List<CartItem> cartList;
    private final OnCartChangedListener listener;

    public ReviewOrderAdapter(List<CartItem> cartList, OnCartChangedListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        holder.tvName.setText(item.name);
        holder.tvPrice.setText(String.format(Locale.US, "Price: $%.2f", item.price));

        if (item.selectedOptions != null && !item.selectedOptions.isEmpty()) {
            holder.tvOptions.setText("Options: " + String.join(", ", item.selectedOptions));
        } else {
            holder.tvOptions.setText("Options: None");
        }

        if (item.note != null && !item.note.isEmpty()) {
            holder.tvNote.setText("Note: " + item.note);
        } else {
            holder.tvNote.setText("Note: None");
        }

        holder.tvQuantity.setText(String.valueOf(item.quantity));

        holder.btnIncreaseQty.setOnClickListener(v -> {
            item.quantity++;
            notifyItemChanged(position);
            listener.onCartChanged();
        });

        holder.btnDecreaseQty.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                notifyItemChanged(position);
                listener.onCartChanged();
            }
        });

        holder.btnRemoveItem.setOnClickListener(v -> {
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvOptions, tvNote, tvQuantity;
        Button btnDecreaseQty, btnIncreaseQty, btnRemoveItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartItemName);
            tvPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvOptions = itemView.findViewById(R.id.tvCartItemOptions);
            tvNote = itemView.findViewById(R.id.tvCartItemNote);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecreaseQty = itemView.findViewById(R.id.btnDecreaseQty);
            btnIncreaseQty = itemView.findViewById(R.id.btnIncreaseQty);
            btnRemoveItem = itemView.findViewById(R.id.btnRemoveItem);
        }
    }
}