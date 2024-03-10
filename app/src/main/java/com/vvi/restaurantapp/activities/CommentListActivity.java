package com.vvi.restaurantapp.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.CommentListAdapter;
import com.vvi.restaurantapp.requests.statistics.GetCommentListRequest;

public class CommentListActivity extends AppCompatActivity {
    private CommentListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        ListView listView = findViewById(R.id.commentList);

        listAdapter = new CommentListAdapter(this, R.layout.comment_list_item);
        listView.setAdapter(listAdapter);

        long dateFrom = getIntent().getLongExtra("dateFrom", 0);
        long dateTo = getIntent().getLongExtra("dateTo", System.currentTimeMillis());

        new GetCommentListRequest(this).execute("" + dateFrom, "" + dateTo);
    }

    public CommentListAdapter getListAdapter() {
        return listAdapter;
    }
}