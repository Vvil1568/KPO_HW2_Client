package com.vvi.restaurantapp.requests.user;

import android.content.Context;
import android.widget.Toast;

import com.vvi.restaurantapp.adapters.UserListAdapter;
import com.vvi.restaurantapp.requests.BasicRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ChangeModeRequest extends BasicRequest {
    private final WeakReference<Context> responseContext;
    private final WeakReference<UserListAdapter> responseAdapter;
    private final int adapterId;
    public ChangeModeRequest(Context context, UserListAdapter activity, int adapterId) {
        super("/user/changeMode","POST");
        this.responseContext = new WeakReference<>(context);
        this.responseAdapter = new WeakReference<>(activity);
        this.adapterId = adapterId;
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response==null){
            Toast.makeText(responseContext.get(), "Произошла ошибка при изменении режима пользователя!\n"+s, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(responseContext.get(), "Режим пользователя успешно изменен!", Toast.LENGTH_SHORT).show();
            responseAdapter.get().getItem(adapterId).changeMode();
            responseAdapter.get().notifyDataSetChanged();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", params[0]);
        return new JSONObject(paramMap);
    }
}