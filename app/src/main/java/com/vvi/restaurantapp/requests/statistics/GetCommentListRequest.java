package com.vvi.restaurantapp.requests.statistics;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.AdminStatisticsActivity;
import com.vvi.restaurantapp.activities.CommentDishListActivity;
import com.vvi.restaurantapp.activities.CommentListActivity;
import com.vvi.restaurantapp.items.Comment;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetCommentListRequest extends BasicRequest {
    private final WeakReference<CommentListActivity> responseActivity;

    public GetCommentListRequest(CommentListActivity activity) {
        super("/stats/commentlist", "POST");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            ArrayList<Comment> comments = SerializationUtils.deserializeCommentList(response);
            if (comments == null) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о получении списка отзывов", Toast.LENGTH_SHORT).show();
            } else {
                responseActivity.get().getListAdapter().addAll(comments);
            }
        } else {
            Toast.makeText(responseActivity.get(), "Произошла ошибка при получении списка отзывов!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("time_from", Long.parseLong(params[0]));
        paramMap.put("time_to", Long.parseLong(params[1]));
        return new JSONObject(paramMap);
    }
}