package com.vvi.restaurantapp.requests.order;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.ShoppingCartActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.ArrayList;

public class GetOrderDishListRequest extends BasicRequest {
    private final WeakReference<ShoppingCartActivity> responseActivity;

    public GetOrderDishListRequest(ShoppingCartActivity activity) {
        super("/order/dishlist", "GET");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            try {
                ArrayList<AbstractMap.SimpleEntry<Dish, Integer>> dishes = new ArrayList<>();
                JSONArray dishesObj = response.getJSONArray("dishes");
                for (int i = 0; i < dishesObj.length(); i++) {
                    JSONObject item = dishesObj.getJSONObject(i);
                    Dish dish = SerializationUtils.deserializeDish(item.getJSONObject("dish"));
                    if (dish != null) {
                        dishes.add(new AbstractMap.SimpleEntry<>(dish, item.getInt("count")));
                    }
                }
                responseActivity.get().getListAdapter().addAll(dishes);
            } catch (JSONException e) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о получении списка блюд", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(responseActivity.get(), "Произошла ошибка при получении списка блюд!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        return new JSONObject();
    }
}