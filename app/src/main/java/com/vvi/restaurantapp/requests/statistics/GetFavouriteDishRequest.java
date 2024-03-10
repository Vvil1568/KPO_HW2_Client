package com.vvi.restaurantapp.requests.statistics;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.AdminStatisticsActivity;
import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class GetFavouriteDishRequest extends BasicRequest {
    private final WeakReference<AdminStatisticsActivity> responseActivity;

    public GetFavouriteDishRequest(AdminStatisticsActivity context) {
        super("/stats/favouritedish", "POST");
        this.responseActivity = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            try {
                String favouriteDish = response.getString("favourite_dish");
                Toast toast = Toast.makeText(responseActivity.get(), "Самое популярное блюдо: " + favouriteDish, Toast.LENGTH_SHORT);
                toast.setMargin(0, 1);
                toast.show();
            } catch (JSONException e) {
                Toast.makeText(responseActivity.get(), "Невозможно определить самое популярное блюдо!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(responseActivity.get(), "Невозможно определить самое популярное блюдо!", Toast.LENGTH_SHORT).show();
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
