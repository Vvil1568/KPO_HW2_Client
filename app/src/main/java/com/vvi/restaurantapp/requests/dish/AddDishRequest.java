package com.vvi.restaurantapp.requests.dish;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.vvi.restaurantapp.activities.MenuActivity;
import com.vvi.restaurantapp.activities.RegistrationActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class AddDishRequest extends BasicRequest {
    private final WeakReference<MenuActivity> responseActivity;

    public AddDishRequest(MenuActivity activity) {
        super("/dish/add", "POST");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            Dish dish = SerializationUtils.deserialize(response);
            if (dish == null) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о добавлении", Toast.LENGTH_SHORT).show();
            }
            responseActivity.get().getListAdapter().add(dish);
        } else {
            Toast.makeText(responseActivity.get(), "Произошла ошибка при добавлении блюда!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", params[0]);
        paramMap.put("desc", params[1]);
        paramMap.put("price", Double.parseDouble(params[2]));
        paramMap.put("time", Long.parseLong(params[3]));
        return new JSONObject(paramMap);
    }
}