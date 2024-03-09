package com.vvi.restaurantapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.dish.AddDishRequest;
import com.vvi.restaurantapp.requests.dish.EditDishRequest;
import com.vvi.restaurantapp.requests.dish.GetDishListRequest;
import com.vvi.restaurantapp.util.SessionStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MenuActivity extends AppCompatActivity {
    private ImageView buttonAdd;
    private Button buttonBuy;
    private ListView listView;
    private DishArrayAdapter listAdapter;
    private String lastImage = "";
    private String lastImageURI = "";
    private static int IMAGE_GET_RESULT = 0;
    private static TextView addImageLabel;
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
                getAddDishDialog().create().show();
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

    public AlertDialog.Builder getAddDishDialog(){
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
        addImageLabel = dialogview.findViewById(R.id.addImage);
        addImageLabel.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_GET_RESULT);
        });
        dialog.setNeutralButton("Добавить", (dialog1, i) -> {
            String name = editName.getText().toString();
            String desc = editDesc.getText().toString();
            String price = editPrice.getText().toString();
            String time = editTime.getText().toString();
            if(name.isEmpty() || price.isEmpty() || time.isEmpty()){
                Toast.makeText(MenuActivity.this, "Заполните поля \"название\", \"цена\", \"время\"", Toast.LENGTH_SHORT).show();
                return;
            }
            new AddDishRequest(MenuActivity.this).execute(name, desc, price, time, lastImage);
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Отмена",(dialog1, i)-> dialog1.dismiss());
        return dialog;
    }

    public AlertDialog.Builder getEditDialog(int pos, Dish dish){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MenuActivity.this);
        dialog.setMessage("Измените блюде:");
        dialog.setTitle("Изменение блюда");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = inflater.inflate(R.layout.dialog_menu_add, null);
        dialog.setView(dialogview);
        EditText editName = dialogview.findViewById(R.id.editName);
        EditText editDesc = dialogview.findViewById(R.id.editDesc);
        EditText editPrice = dialogview.findViewById(R.id.editPrice);
        EditText editTime = dialogview.findViewById(R.id.editTime);
        editName.setText(dish.getName());
        editDesc.setText(dish.getDescription());
        editPrice.setText(""+dish.getPrice());
        editTime.setText(""+dish.getTime());
        addImageLabel = dialogview.findViewById(R.id.addImage);
        addImageLabel.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_GET_RESULT);
        });
        dialog.setNeutralButton("Изменить", (dialog1, i) -> {
            String name = editName.getText().toString();
            String desc = editDesc.getText().toString();
            String price = editPrice.getText().toString();
            String time = editTime.getText().toString();
            if(name.isEmpty() || price.isEmpty() || time.isEmpty()){
                Toast.makeText(MenuActivity.this, "Заполните поля \"название\", \"цена\", \"время\"", Toast.LENGTH_SHORT).show();
                return;
            }
            new EditDishRequest(MenuActivity.this, listAdapter, pos).execute(""+dish.getId(), name, desc, price, time, lastImage);
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Отмена",(dialog1, i)-> dialog1.dismiss());
        return dialog;
    }

    public DishArrayAdapter getListAdapter() {
        return listAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_GET_RESULT && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                lastImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                lastImageURI = data.getData().toString();
                addImageLabel.setText(lastImageURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}