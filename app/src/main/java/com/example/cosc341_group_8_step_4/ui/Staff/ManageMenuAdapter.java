package com.example.cosc341_group_8_step_4.ui.Staff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.MenuItem;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class ManageMenuAdapter extends RecyclerView.Adapter<ManageMenuAdapter.ViewHolder> {

    private final List<MenuItem> menuList;
    private final Context context;

    public ManageMenuAdapter(List<MenuItem> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = menuList.get(position);

        holder.tvName.setText(item.name);
        holder.tvCategory.setText("Category: " + item.category);
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", item.price));
        if (item.isAvailable) {
            holder.tvStatus.setText("Available");
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.tvStatus.setText("Unavailable");
            holder.tvStatus.setTextColor(Color.parseColor("#D32F2F"));
            holder.itemView.setAlpha(0.5f);
        }

        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            File file = new File(item.imagePath);
            if (file.exists()) {
                holder.imgItem.setImageURI(Uri.fromFile(file));
            } else if (item.imageResId != 0) {
                holder.imgItem.setImageResource(item.imageResId);
            } else {
                holder.imgItem.setImageResource(R.drawable.res_logo);
            }
        } else if (item.imageResId != 0) {
            holder.imgItem.setImageResource(item.imageResId);
        } else {
            holder.imgItem.setImageResource(R.drawable.res_logo);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditMenuItemActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("itemIndex", holder.getAdapterPosition());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            AppData.deleteMenuItem(currentPosition);
                            notifyItemRemoved(currentPosition);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvName, tvCategory, tvPrice, tvStatus;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgManageItem);
            tvName = itemView.findViewById(R.id.tvManageItemName);
            tvCategory = itemView.findViewById(R.id.tvManageItemCategory);
            tvPrice = itemView.findViewById(R.id.tvManageItemPrice);
            tvStatus = itemView.findViewById(R.id.tvManageItemStatus);
            btnEdit = itemView.findViewById(R.id.btnEditItem);
            btnDelete = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}