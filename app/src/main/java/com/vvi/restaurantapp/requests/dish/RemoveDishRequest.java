package com.vvi.restaurantapp.requests.dish;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RemoveDishRequest extends BasicRequest {
    private final WeakReference<DishArrayAdapter> responseAdapter;
    private final WeakReference<Context> responseContext;
    private final int adapterId;

    public RemoveDishRequest(Context context, DishArrayAdapter activity, int adapterId) {
        super("/dish/remove","POST");
        this.responseAdapter = new WeakReference<>(activity);
        this.responseContext = new WeakReference<>(context);
        this.adapterId = adapterId;
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response!=null){
            responseAdapter.get().removeId(adapterId);
        }else{
            Toast.makeText(responseContext.get(), "Произошла ошибка при удалении блюда!\n"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", Integer.parseInt(params[0]));
        return new JSONObject(paramMap);
    }
}