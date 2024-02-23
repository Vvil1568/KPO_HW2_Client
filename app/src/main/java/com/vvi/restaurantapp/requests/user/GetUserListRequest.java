package com.vvi.restaurantapp.requests.user;

import android.widget.Toast;

import com.vvi.restaurantapp.activities.MenuActivity;
import com.vvi.restaurantapp.activities.UserListActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.items.User;
import com.vvi.restaurantapp.requests.BasicRequest;
import com.vvi.restaurantapp.util.SerializationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetUserListRequest extends BasicRequest {
    private final WeakReference<UserListActivity> responseActivity;

    public GetUserListRequest(UserListActivity activity) {
        super("/user/getList","GET");
        this.responseActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject response = readAsJson(s);
        if(response!=null){
            try {
                ArrayList<User> users = new ArrayList<>();
                JSONArray userListObj = response.getJSONArray("users");
                for(int i=0;i<userListObj.length();i++){
                    User user = SerializationUtils.deserializeUser(userListObj.getJSONObject(i));
                    if(user!=null) {
                        users.add(user);
                    }
                }
                responseActivity.get().getListAdapter().addAll(users);
            }catch (JSONException e){
                Toast.makeText(responseActivity.get(), "Невозможно считать ответ на запрос о получении списка пользователей", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(responseActivity.get(), "Произошла ошибка при получении списка пользователей!\n"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected JSONObject paramsIntoJson(String... params) {
        return new JSONObject();
    }
}