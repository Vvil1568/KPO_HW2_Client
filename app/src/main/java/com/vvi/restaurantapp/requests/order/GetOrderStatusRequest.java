package com.vvi.restaurantapp.requests.order;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetOrderStatusRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;

    public GetOrderStatusRequest (Context context) {
        super("/order/getstatus","GET");
        this.responseContext = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response!=null) {
            try {
                String status = response.getString("status");
                Toast.makeText(responseContext.get(), status, Toast.LENGTH_SHORT).show();
            } catch (JSONException exception) {
                Toast.makeText(responseContext.get(), "Произошла ошибка при выводе статуса!\n" + s, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(responseContext.get(), "Произошла ошибка при выводе статуса!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        return new JSONObject();
    }
}