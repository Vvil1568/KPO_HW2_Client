package com.vvi.restaurantapp.requests.dish;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.MenuActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetDishListRequest extends BasicRequest {
    private final WeakReference<MenuActivity> responseActivity;

    public GetDishListRequest(MenuActivity activity) {
        super("/dish/get", "GET");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            ArrayList<Dish> dishes = SerializationUtils.deserializeDishList(response);
            if (dishes == null) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о получении списка блюд", Toast.LENGTH_SHORT).show();
            } else {
                responseActivity.get().getListAdapter().addAll(dishes);
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