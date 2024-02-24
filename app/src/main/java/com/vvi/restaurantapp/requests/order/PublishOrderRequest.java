package com.vvi.restaurantapp.requests.order;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class PublishOrderRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;

    public PublishOrderRequest(Context context) {
        super("/order/postorder","POST");
        this.responseContext = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response==null){
            Toast.makeText(responseContext.get(), "Произошла ошибка при добавлении блюда в корзину!\n"+s, Toast.LENGTH_SHORT).show();
        }else{
            Toast toast = Toast.makeText(responseContext.get(), "Заказ успешно оформлен!", Toast.LENGTH_SHORT);
            toast.setMargin(0,1);
            toast.show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        return new JSONObject(paramMap);
    }
}