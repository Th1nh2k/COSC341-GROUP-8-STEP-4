package com.example.cosc341_group_8_step_4.ui.Customer;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.model.MenuItem;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final List<MenuItem> menuList;

    public MenuAdapter(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = menuList.get(position);

        holder.name.setText(item.name);
        holder.description.setText(item.description);
        holder.price.setText(String.format(Locale.US, "$%.2f", item.price));

        if (item.isAvailable) {
            holder.addBtn.setEnabled(true);
            holder.addBtn.setText("Add");
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.addBtn.setEnabled(false);
            holder.addBtn.setText("Unavailable");
            holder.itemView.setAlpha(0.5f);
        }

        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            File file = new File(item.imagePath);
            if (file.exists()) {
                holder.image.setImageURI(Uri.fromFile(file));
            } else if (item.imageResId != 0) {
                holder.image.setImageResource(item.imageResId);
            } else {
                holder.image.setImageResource(R.drawable.res_logo);
            }
        } else if (item.imageResId != 0) {
            holder.image.setImageResource(item.imageResId);
        } else {
            holder.image.setImageResource(R.drawable.res_logo);
        }

        holder.addBtn.setOnClickListener(v -> {
            if (!item.isAvailable) {
                return;
            }
            Intent intent = new Intent(v.getContext(), ModificationItemActivity.class);
            intent.putExtra("itemName", item.name);
            intent.putExtra("itemPrice", item.price);
            intent.putExtra("itemOptions", item.options);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price;
        ImageView image;
        Button addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgItem);
            name = itemView.findViewById(R.id.tvItemName);
            description = itemView.findViewById(R.id.tvItemDescription);
            price = itemView.findViewById(R.id.tvItemPrice);
            addBtn = itemView.findViewById(R.id.btnAddItem);
        }
    }
}