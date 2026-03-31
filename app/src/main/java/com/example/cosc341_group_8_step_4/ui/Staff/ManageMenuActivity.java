package com.example.cosc341_group_8_step_4.ui.Staff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;

public class ManageMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerManageMenu;
    private Button btnAddItem, btnBack;
    private ManageMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        recyclerManageMenu = findViewById(R.id.recyclerManageMenu);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnBack = findViewById(R.id.btnBackManageMenu);

        adapter = new ManageMenuAdapter(AppData.menuItems, this);
        recyclerManageMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerManageMenu.setAdapter(adapter);

        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(ManageMenuActivity.this, AddEditMenuItemActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}