package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vvi.restaurantapp.R;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        findViewById(R.id.placeOrder).setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}