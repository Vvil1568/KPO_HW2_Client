package com.vvi.restaurantapp.requests.order;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CancelOrderRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;

    public CancelOrderRequest(Context context) {
        super("/order/cancelorder", "POST");
        this.responseContext = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        Toast toast;
        if (response == null) {
            toast = Toast.makeText(responseContext.get(), "Произошла ошибка при отмене заказа!\n" + s, Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(responseContext.get(), "Заказ успешно отменен!", Toast.LENGTH_SHORT);
        }
        toast.setMargin(0, 1);
        toast.show();
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        return new JSONObject(paramMap);
    }
}