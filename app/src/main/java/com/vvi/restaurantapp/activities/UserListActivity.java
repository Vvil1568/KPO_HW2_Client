package com.vvi.restaurantapp.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.UserListAdapter;
import com.vvi.restaurantapp.requests.user.GetUserListRequest;

public class UserListActivity extends AppCompatActivity {
    private UserListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListView listView = findViewById(R.id.userList);

        listAdapter = new UserListAdapter(this, R.layout.user_list_item);
        listView.setAdapter(listAdapter);
        new GetUserListRequest(this).execute();
    }
    public UserListAdapter getListAdapter() {
        return listAdapter;
    }
}