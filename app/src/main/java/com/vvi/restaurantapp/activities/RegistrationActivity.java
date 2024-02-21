package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.requests.LoginRequest;
import com.vvi.restaurantapp.requests.RegisterRequest;
import com.vvi.restaurantapp.util.HashUtils;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fioEditText;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fioEditText = findViewById(R.id.registration_fio);
        loginEditText = findViewById(R.id.registration_login);
        passwordEditText = findViewById(R.id.registration_password);

        findViewById(R.id.registration_register).setOnClickListener(v -> {
            String fio = fioEditText.getText().toString();
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(login.isEmpty()){
                Toast.makeText(this, "Введите логин!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Введите пароль!", Toast.LENGTH_SHORT).show();
                return;
            }
            new RegisterRequest(this).execute(fio, login, HashUtils.hashString(password));
        });
    }
}