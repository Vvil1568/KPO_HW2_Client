package com.vvi.restaurantapp.requests.authentification;

import android.content.Intent;
import android.widget.Toast;

import com.vvi.restaurantapp.activities.AdminMainActivity;
import com.vvi.restaurantapp.activities.RegistrationActivity;
import com.vvi.restaurantapp.activities.UserMainActivity;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SessionStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends BasicRequest {
    private final WeakReference<RegistrationActivity> responseActivity;

    public RegisterRequest(RegistrationActivity activity) {
        super("/registration", "POST");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if (response != null) {
            try {
                SessionStorage.token = response.getString("token");
                SessionStorage.isAdmin = response.getBoolean("isAdmin");
                Intent intent;
                if (SessionStorage.isAdmin) {
                    intent = new Intent(responseActivity.get(), AdminMainActivity.class);
                } else {
                    intent = new Intent(responseActivity.get(), UserMainActivity.class);
                }
                responseActivity.get().startActivity(intent);
            } catch (JSONException e) {
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о регистрации", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(responseActivity.get(), "Произошла ошибка при попытке регистрации!\n" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("fio", params[0]);
        paramMap.put("login", params[1]);
        paramMap.put("passHash", params[2]);
        return new JSONObject(paramMap);
    }
}