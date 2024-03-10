package com.vvi.restaurantapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.requests.statistics.GetFavouriteDishRequest;
import com.vvi.restaurantapp.requests.statistics.GetOrderCountRequest;
import com.vvi.restaurantapp.requests.statistics.GetTotalRevenueRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class AdminStatisticsActivity extends AppCompatActivity {
    private long dateFrom;
    private long dateTo;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        dateFrom = 0;
        dateTo = System.currentTimeMillis();

        findViewById(R.id.statsDateFrom).setOnClickListener(v -> {
            getSelectDate((time) -> {
                dateFrom = time;
                Date date = new Date(dateFrom);
                ((TextView) findViewById(R.id.statsDateFrom)).setText(dateFormat.format(date));
            }).show();
        });

        findViewById(R.id.statsDateTo).setOnClickListener(v -> {
            getSelectDate((time) -> {
                dateTo = time;
                Date date = new Date(dateTo);
                ((TextView) findViewById(R.id.statsDateTo)).setText(dateFormat.format(date));
            }).show();
        });

        findViewById(R.id.getTotalRevenue).setOnClickListener(v -> {
            new GetTotalRevenueRequest(this).execute("" + dateFrom, "" + dateTo);
        });

        findViewById(R.id.getOrderCount).setOnClickListener(v -> {
            new GetOrderCountRequest(this).execute("" + dateFrom, "" + dateTo);
        });

        findViewById(R.id.getFavouriteDish).setOnClickListener(v -> {
            new GetFavouriteDishRequest(this).execute("" + dateFrom, "" + dateTo);
        });

        findViewById(R.id.showCommentList).setOnClickListener(v -> {
            Intent intent = new Intent(AdminStatisticsActivity.this, CommentListActivity.class);
            intent.putExtra("dateFrom", dateFrom);
            intent.putExtra("dateTo", dateTo);
            startActivity(intent);
        });

        findViewById(R.id.showRatingsList).setOnClickListener(v -> {
            Intent intent = new Intent(AdminStatisticsActivity.this, RatingListActivity.class);
            intent.putExtra("dateFrom", dateFrom);
            intent.putExtra("dateTo", dateTo);
            startActivity(intent);
        });
    }

    public AlertDialog getSelectDate(Consumer<Long> action) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AdminStatisticsActivity.this);
        dialog.setMessage("Выберите дату");
        dialog.setTitle("Выбор даты");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = inflater.inflate(R.layout.dialog_date_selection, null);
        dialog.setView(dialogview);
        CalendarView calendar = dialogview.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            action.accept(new Date(year - 1900, month, dayOfMonth).getTime());
        });
        dialog.setPositiveButton("Закрыть", (dialog1, i) -> dialog1.dismiss());
        return dialog.create();
    }
}