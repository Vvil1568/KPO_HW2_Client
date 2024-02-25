package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vvi.restaurantapp.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.adminMenu).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.adminUserList).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.adminStatistics).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, AdminStatisticsActivity.class);
            startActivity(intent);
        });
    }
}