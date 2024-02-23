package com.vvi.restaurantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.adapters.DishArrayAdapter;
import com.vvi.restaurantapp.requests.comment.LeaveCommentRequest;
import com.vvi.restaurantapp.requests.order.GetCommentDishListRequest;

import java.util.concurrent.atomic.AtomicInteger;

public class CommentDishListActivity extends AppCompatActivity {
    private ListView listView;
    private DishArrayAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_dish_list);

        listView = findViewById(R.id.commentDishList);

        listAdapter = new DishArrayAdapter(this, R.layout.comment_dish_list_item);
        listView.setAdapter(listAdapter);

        new GetCommentDishListRequest(this).execute();
    }

    public static AlertDialog getAddCommentDialog(Context context, int dish_id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Оставьте отзыв на данное блюдо:");
        dialog.setTitle("Написание отзыва");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = inflater.inflate(R.layout.dialog_comment, null);
        dialog.setView(dialogview);

        AtomicInteger stars = new AtomicInteger();
        int[] starsIds = new int[] { R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5};
        for(int i=0;i<starsIds.length;i++){
            int finalI = i;
            dialogview.findViewById(starsIds[i]).setOnClickListener(v->{
                stars.set(finalI + 1);
                for(int j=0;j<=finalI;j++) {
                    ((ImageView)dialogview.findViewById(starsIds[j])).setImageResource(R.drawable.star_on);
                }
                for(int j=finalI+1;j<starsIds.length;j++) {
                    ((ImageView)dialogview.findViewById(starsIds[j])).setImageResource(R.drawable.star_off);
                }
            });
        }

        EditText comment = dialogview.findViewById(R.id.commentDialogComment);
        dialog.setNeutralButton("Отправить", (dialog1, i) -> {
            if(stars.get()!=0) {
                String commentStr = comment.getText().toString();
                new LeaveCommentRequest(context).execute(""+dish_id, ""+stars, commentStr);
            }else{
                Toast.makeText(context, "Выберите количество звезд", Toast.LENGTH_SHORT).show();
            }
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Отмена",(dialog1, i)-> dialog1.dismiss());
        return dialog.create();
    }

    public DishArrayAdapter getListAdapter() {
        return listAdapter;
    }
}