package com.vvi.restaurantapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.activities.CommentDishListActivity;
import com.vvi.restaurantapp.activities.MenuActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.dish.RemoveDishRequest;
import com.vvi.restaurantapp.requests.order.BuyDishRequest;
import com.vvi.restaurantapp.util.SessionStorage;

public class DishArrayAdapter extends ArrayAdapter<Dish> {
    private final int itemResourceType;

    public DishArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemResourceType = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Dish dish = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (convertView == null) {
            view = inflater.inflate(itemResourceType, parent, false);
        } else {
            view = convertView;
        }

        TextView name;
        TextView desc;
        if(itemResourceType == R.layout.menu_list_item) {
            name = view.findViewById(R.id.menuItemName);
            desc = view.findViewById(R.id.menuItemDesc);
        }else{
            name = view.findViewById(R.id.commentDishListName);
            desc = view.findViewById(R.id.commentDishListDesc);
            view.setOnClickListener(v->{
                CommentDishListActivity.getAddCommentDialog(getContext(), dish.getId()).show();
            });
        }

        name.setText(dish.getName());
        desc.setText(dish.getDescription());

        if(getContext() instanceof MenuActivity) {
            TextView price = view.findViewById(R.id.menuItemPrice);
            ImageView buyButton = view.findViewById(R.id.menuItemBuy);
            ImageView deleteButton = view.findViewById(R.id.menuItemDelete);
            ImageView editButton = view.findViewById(R.id.editDish);
            ImageView dishPhoto = view.findViewById(R.id.dishPhoto);

            price.setText("" + dish.getPrice());
            byte[] decodedString = Base64.decode(dish.getImage(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            dishPhoto.setImageBitmap(decodedBitmap);

            if (SessionStorage.isAdmin) {
                buyButton.setVisibility(View.GONE);
                deleteButton.setOnClickListener(v -> {
                    new RemoveDishRequest(getContext(), this, position).execute("" + dish.getId());
                });
                editButton.setOnClickListener(v -> {
                    ((MenuActivity)getContext()).getEditDialog(position,dish).create().show();
                });
            } else {
                deleteButton.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);
                buyButton.setOnClickListener(v -> {
                    new BuyDishRequest(getContext()).execute("" + dish.getId());
                });
            }
        }
        return view;
    }

    public void removeId(int adapterId){
        remove(getItem(adapterId));
    }
}
