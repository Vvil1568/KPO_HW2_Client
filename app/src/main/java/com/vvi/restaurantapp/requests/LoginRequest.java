package com.vvi.restaurantapp.requests;

import android.content.Intent;
import android.widget.Toast;

import com.vvi.restaurantapp.activities.LoginActivity;
import com.vvi.restaurantapp.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends BasicRequest{
    private final WeakReference<LoginActivity> responseTextViewRef;

    public LoginRequest(LoginActivity activity) {
        super("/login","POST");
        this.responseTextViewRef = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response!=null){
            try {
                String token = response.getString("token");
                Boolean isAdmin = response.getBoolean("isAdmin");
                Intent intent = new Intent(responseTextViewRef.get(), MainActivity.class);
                responseTextViewRef.get().startActivity(intent);
            }catch (JSONException e){
                Toast.makeText(responseTextViewRef.get(), "Невозможно считать ответ на запрос о входе", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(responseTextViewRef.get(), "Произошла ошибка при попытке входа!\n"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("login",params[0]);
        paramMap.put("passHash",params[1]);
        return new JSONObject(paramMap);
    }
}