package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.CommentListAdapter;
import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.requests.statistics.GetCommentListRequest;

public class CommentListActivity extends AppCompatActivity {
    private long dateFrom;
    private long dateTo;
    private ListView listView;
    private CommentListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        listView = findViewById(R.id.commentList);

        listAdapter = new CommentListAdapter(this, R.layout.comment_list_item);
        listView.setAdapter(listAdapter);

        dateFrom = getIntent().getLongExtra("dateFrom",0);
        dateTo = getIntent().getLongExtra("dateTo",System.currentTimeMillis());

        new GetCommentListRequest(this).execute(""+dateFrom,""+dateTo);
    }

    public CommentListAdapter getListAdapter() {
        return listAdapter;
    }
}