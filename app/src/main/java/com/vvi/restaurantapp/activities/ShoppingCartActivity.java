package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.ShoppingCartAdapter;
import com.vvi.restaurantapp.requests.order.GetOrderDishListRequest;

public class ShoppingCartActivity extends AppCompatActivity {
    private ListView listView;
    private ShoppingCartAdapter listAdapter;
    private Button postOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        listView = findViewById(R.id.cartList);
        postOrder = findViewById(R.id.cartPlaceOrder);

        listAdapter = new ShoppingCartAdapter(this, R.layout.cart_list_item);
        listView.setAdapter(listAdapter);
        new GetOrderDishListRequest(this).execute();

        postOrder.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, UserMainActivity.class);
            startActivity(intent);
            Toast toast = Toast.makeText(this, "Заказ успешно оформлен!", Toast.LENGTH_SHORT);
            toast.setMargin(0,50);
            toast.show();
        });
    }

    public ShoppingCartAdapter getListAdapter() {
        return listAdapter;
    }
}