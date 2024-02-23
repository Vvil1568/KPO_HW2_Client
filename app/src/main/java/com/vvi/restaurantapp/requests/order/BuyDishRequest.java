package com.vvi.restaurantapp.requests.order;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class BuyDishRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;

    public BuyDishRequest(Context context) {
        super("/order/adddish","POST");
        this.responseContext = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response==null){
            Toast.makeText(responseContext.get(), "Произошла ошибка при добавлении блюда в корзину!\n"+s, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(responseContext.get(), "Блюдо добавлено в корзину!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dish_id", Integer.parseInt(params[0]));
        return new JSONObject(paramMap);
    }
}