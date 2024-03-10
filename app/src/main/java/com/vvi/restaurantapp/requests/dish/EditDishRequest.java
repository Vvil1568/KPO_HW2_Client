package com.vvi.restaurantapp.requests.dish;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class EditDishRequest extends BasicRequest {
    private final WeakReference<DishArrayAdapter> responseAdapter;
    private final WeakReference<Context> responseContext;
    private final int adapterId;

    public EditDishRequest(Context context, DishArrayAdapter activity, int adapterId) {
        super("/dish/edit", "POST");
        this.responseAdapter = new WeakReference<>(activity);
        this.responseContext = new WeakReference<>(context);
        this.adapterId = adapterId;
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            Dish dish = SerializationUtils.deserializeDish(response);
            if (dish == null) {
                Toast.makeText(responseContext.get(), "Невозможно считать ответ на запрос об изменении", Toast.LENGTH_SHORT).show();
                return;
            }
            responseAdapter.get().getItem(adapterId).copy(dish);
            responseAdapter.get().notifyDataSetChanged();
        } else {
            Toast.makeText(responseContext.get(), "Произошла ошибка при изменении блюда!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", Integer.parseInt(params[0]));
        paramMap.put("name", params[1]);
        paramMap.put("desc", params[2]);
        paramMap.put("price", Double.parseDouble(params[3]));
        paramMap.put("time", Long.parseLong(params[4]));
        paramMap.put("image", params[5]);
        return new JSONObject(paramMap);
    }
}