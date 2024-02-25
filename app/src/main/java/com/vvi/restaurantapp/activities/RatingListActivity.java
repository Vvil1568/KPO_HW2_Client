package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.CommentListAdapter;
import com.vvi.restaurantapp.adapters.RatingListAdapter;
import com.vvi.restaurantapp.requests.statistics.GetCommentListRequest;
import com.vvi.restaurantapp.requests.statistics.GetRatingListRequest;

public class RatingListActivity extends AppCompatActivity {
    private long dateFrom;
    private long dateTo;
    private ListView listView;
    private RatingListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_list);

        listView = findViewById(R.id.ratingList);

        listAdapter = new RatingListAdapter(this, R.layout.ratings_list_item);
        listView.setAdapter(listAdapter);

        dateFrom = getIntent().getLongExtra("dateFrom",0);
        dateTo = getIntent().getLongExtra("dateTo",System.currentTimeMillis());

        new GetRatingListRequest(this).execute(""+dateFrom,""+dateTo);
    }

    public RatingListAdapter getListAdapter() {
        return listAdapter;
    }
}