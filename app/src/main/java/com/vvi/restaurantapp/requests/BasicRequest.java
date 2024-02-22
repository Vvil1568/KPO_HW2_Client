package com.vvi.restaurantapp.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.vvi.restaurantapp.util.SessionStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public abstract class BasicRequest extends AsyncTask<String, Void, String> {
    private final String method;
    private final String endpoint;

    public BasicRequest(String endpoint, String method){
        this.method = method;
        this.endpoint = endpoint;
    }

    protected abstract JSONObject paramsIntoJson(String... params);

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = createRequest(getURL(), paramsIntoJson(params).toString());
        if (conn == null) return "";
        return readResponse(conn);
    }

    private HttpURLConnection createRequest(String address, String toSend) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if(!Objects.equals(SessionStorage.token, "")){
                conn.setRequestProperty("Authorization", "Bearer " + SessionStorage.token);
            }
            if(!method.equals("GET")) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStream os = conn.getOutputStream();
                byte[] input = toSend.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return conn;
        } catch (Exception e) {
            Log.e("RestaurantApp", "Can't send post request to url " + address, e);
            e.printStackTrace();
            return null;
        }
    }

    private String readResponse(HttpURLConnection conn) {
        try {
            InputStream istream;
            if(conn.getResponseCode()==200){
                istream = conn.getInputStream();
            }else{
                istream = conn.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("RestaurantApp", "Server returned bad http code " + conn.getResponseCode() + " with error: " + response);
            }
            return response.toString();
        } catch (Exception e) {
            Log.e("RestaurantApp", "Can't read response from url " + conn.getURL(), e);
            return "";
        }
    }

    protected JSONObject readAsJson(String obj){
        try {
            return new JSONObject(obj);
        }catch (JSONException e){
            return null;
        }
    }

    private String getURL(){
        return "http://192.168.1.66:8080"+endpoint;
    }
}