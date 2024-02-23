package com.vvi.restaurantapp.requests.order;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.ShoppingCartActivity;
import com.vvi.restaurantapp.adapters.ShoppingCartAdapter;
import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ChangeDishCountRequest extends BasicRequest {
    private final WeakReference<ShoppingCartAdapter> responseAdapter;
    private final WeakReference<ShoppingCartActivity> responseActivity;
    private final int adapterId;

    public ChangeDishCountRequest(ShoppingCartActivity context, ShoppingCartAdapter activity, int adapterId) {
        super("/order/changedishcount","POST");
        this.responseAdapter = new WeakReference<>(activity);
        this.responseActivity = new WeakReference<>(context);
        this.adapterId = adapterId;
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response!=null){
            try {
                int newCount = response.getInt("new_count");
                if(newCount>0) {
                    responseAdapter.get().getItem(adapterId).setValue(newCount);
                    responseAdapter.get().notifyDataSetChanged();
                }else{
                    responseAdapter.get().removeId(adapterId);
                }
            } catch (JSONException e) {
                Toast.makeText(responseActivity.get(), "Произошла ошибка при изменении количества блюда!\n"+s, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(responseActivity.get(), "Произошла ошибка при изменении количества блюда!\n"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dish_id", Integer.parseInt(params[0]));
        paramMap.put("delta", Integer.parseInt(params[1]));
        return new JSONObject(paramMap);
    }
}