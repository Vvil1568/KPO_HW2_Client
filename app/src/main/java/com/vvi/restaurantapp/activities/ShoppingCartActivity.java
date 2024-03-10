package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.ShoppingCartAdapter;
import com.vvi.restaurantapp.requests.order.GetOrderDishListRequest;
import com.vvi.restaurantapp.requests.order.PublishOrderRequest;

public class ShoppingCartActivity extends AppCompatActivity {
    private ShoppingCartAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        ListView listView = findViewById(R.id.cartList);
        Button postOrder = findViewById(R.id.cartPlaceOrder);

        listAdapter = new ShoppingCartAdapter(this, R.layout.cart_list_item);
        listView.setAdapter(listAdapter);
        new GetOrderDishListRequest(this).execute();

        postOrder.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, UserMainActivity.class);
            startActivity(intent);
            new PublishOrderRequest(this).execute();
        });
    }

    public ShoppingCartAdapter getListAdapter() {
        return listAdapter;
    }
}