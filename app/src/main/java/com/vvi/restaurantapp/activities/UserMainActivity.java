package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.requests.order.CancelOrderRequest;
import com.vvi.restaurantapp.requests.order.GetOrderStatusRequest;
import com.vvi.restaurantapp.requests.order.PayForOrderRequest;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        findViewById(R.id.placeOrder).setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.orderStatus).setOnClickListener(v -> {
            new GetOrderStatusRequest(this).execute();
        });

        findViewById(R.id.payForOrder).setOnClickListener(v -> {
            new PayForOrderRequest(this).execute();
        });

        findViewById(R.id.cancelOrder).setOnClickListener(v -> {
            new CancelOrderRequest(this).execute();
        });

        findViewById(R.id.leaveComment).setOnClickListener(v->{
            Intent intent = new Intent(UserMainActivity.this, CommentDishListActivity.class);
            startActivity(intent);
        });
    }
}