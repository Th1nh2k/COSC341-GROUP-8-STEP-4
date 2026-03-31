package com.example.cosc341_group_8_step_4.ui.Staff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;

public class StaffLoginActivity extends AppCompatActivity {

    private static final String STAFF_PASSWORD = "1234";

    private EditText etPassword;
    private Button btnLogin, btnBack;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        tvError = findViewById(R.id.tvError);

        btnLogin.setOnClickListener(v -> {
            String input = etPassword.getText().toString().trim();

            if (input.equals(STAFF_PASSWORD)) {
                tvError.setText("");
                Intent intent = new Intent(StaffLoginActivity.this, StaffDashboardActivity.class);
                startActivity(intent);
            } else {
                tvError.setText("Incorrect password. Please try again.");
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}