package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.requests.authentification.LoginRequest;
import com.vvi.restaurantapp.util.HashUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = findViewById(R.id.login_login);
        passwordEditText = findViewById(R.id.login_password);

        findViewById(R.id.login_register).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.login_log_in).setOnClickListener(v -> {
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
            new LoginRequest(this).execute(login, HashUtils.hashString(password));
        });
    }
}