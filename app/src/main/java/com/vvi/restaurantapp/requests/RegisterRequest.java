package com.vvi.restaurantapp.requests;

import android.content.Intent;
import android.widget.Toast;

import com.vvi.restaurantapp.activities.LoginActivity;
import com.vvi.restaurantapp.activities.MainActivity;
import com.vvi.restaurantapp.activities.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends BasicRequest{
    private final WeakReference<RegistrationActivity> responseTextViewRef;

    public RegisterRequest(RegistrationActivity activity) {
        super("/registration","POST");
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
                Toast.makeText(responseTextViewRef.get(), "Невозможно считать ответ на запрос о регистрации", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(responseTextViewRef.get(), "Произошла ошибка при попытке регистрации!\n"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("fio",params[0]);
        paramMap.put("login",params[1]);
        paramMap.put("passHash",params[2]);
        return new JSONObject(paramMap);
    }
}