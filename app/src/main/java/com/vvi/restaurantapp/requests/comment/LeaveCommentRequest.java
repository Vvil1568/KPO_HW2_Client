package com.vvi.restaurantapp.requests.comment;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LeaveCommentRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;

    public LeaveCommentRequest(Context context) {
        super("/comment/add", "POST");
        this.responseContext = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            Toast.makeText(responseContext.get(), "Отзыв успешно оставлен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(responseContext.get(), "Произошла ошибка при добавлении блюда!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dish_id", Integer.parseInt(params[0]));
        paramMap.put("stars", Integer.parseInt(params[1]));
        paramMap.put("comment", params[2]);
        return new JSONObject(paramMap);
    }
}