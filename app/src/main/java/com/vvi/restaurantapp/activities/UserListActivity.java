package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.ShoppingCartAdapter;
import com.vvi.restaurantapp.adapters.UserListAdapter;
import com.vvi.restaurantapp.requests.order.GetOrderDishListRequest;
import com.vvi.restaurantapp.requests.user.GetUserListRequest;

public class UserListActivity extends AppCompatActivity {
    private ListView listView;
    private UserListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView = findViewById(R.id.userList);

        listAdapter = new UserListAdapter(this, R.layout.user_list_item);
        listView.setAdapter(listAdapter);
        new GetUserListRequest(this).execute();
    }
    public UserListAdapter getListAdapter() {
        return listAdapter;
    }
}