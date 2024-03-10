package com.vvi.restaurantapp.requests.statistics;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.RatingListActivity;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetRatingListRequest extends BasicRequest {
    private final WeakReference<RatingListActivity> responseActivity;

    public GetRatingListRequest(RatingListActivity activity) {
        super("/stats/ratings", "POST");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            ArrayList<AbstractMap.SimpleEntry<String, Double>> ratings = SerializationUtils.deserializeRatingList(response);

            if (ratings == null) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о получении списка рейтингов", Toast.LENGTH_SHORT).show();
            } else {
                responseActivity.get().getListAdapter().addAll(ratings);
            }
        } else {
            Toast.makeText(responseActivity.get(), "Произошла ошибка при получении списка рейтингов!\n" + s, Toast.LENGTH_SHORT).show();
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