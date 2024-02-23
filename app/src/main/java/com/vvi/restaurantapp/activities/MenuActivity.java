package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.requests.dish.AddDishRequest;
import com.vvi.restaurantapp.requests.dish.GetDishListRequest;
import com.vvi.restaurantapp.util.SessionStorage;

public class MenuActivity extends AppCompatActivity {
    private ImageView buttonAdd;
    private Button buttonBuy;
    private ListView listView;
    private DishArrayAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonAdd = findViewById(R.id.menuAdd);
        buttonBuy = findViewById(R.id.menuBuy);
        listView = findViewById(R.id.menuList);

        if(SessionStorage.isAdmin){
            buttonBuy.setVisibility(ImageView.GONE);
            buttonAdd.setOnClickListener(v -> {
                getAddDishDialog().show();
            });
        }else{
            buttonAdd.setVisibility(ImageView.GONE);
            buttonBuy.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            });
        }

        listAdapter = new DishArrayAdapter(this, R.layout.menu_list_item);
        listView.setAdapter(listAdapter);
        new GetDishListRequest(this).execute();
    }

    public AlertDialog getAddDishDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MenuActivity.this);
        dialog.setMessage("Введите данные о новом блюде:");
        dialog.setTitle("Добавление нового блюда");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = inflater.inflate(R.layout.dialog_menu_add, null);
        dialog.setView(dialogview);
        EditText editName = dialogview.findViewById(R.id.editName);
        EditText editDesc = dialogview.findViewById(R.id.editDesc);
        EditText editPrice = dialogview.findViewById(R.id.editPrice);
        EditText editTime = dialogview.findViewById(R.id.editTime);
        dialog.setNeutralButton("Добавить", (dialog1, i) -> {
            String name = editName.getText().toString();
            String desc = editDesc.getText().toString();
            String price = editPrice.getText().toString();
            String time = editTime.getText().toString();
            new AddDishRequest(MenuActivity.this).execute(name, desc, price, time);
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Отмена",(dialog1, i)-> dialog1.dismiss());
        return dialog.create();
    }

    public DishArrayAdapter getListAdapter() {
        return listAdapter;
    }
}