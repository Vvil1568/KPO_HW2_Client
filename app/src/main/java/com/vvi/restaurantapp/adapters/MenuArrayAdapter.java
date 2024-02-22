package com.vvi.restaurantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.dish.RemoveDishRequest;
import com.vvi.restaurantapp.util.SessionStorage;

public class MenuArrayAdapter extends ArrayAdapter<Dish> {
    private int itemResourceType;

    public MenuArrayAdapter(@NonNull Context context, int resource) {
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
        TextView name = view.findViewById(R.id.menuItemName);
        TextView desc = view.findViewById(R.id.menuItemDesc);
        TextView price = view.findViewById(R.id.menuItemPrice);
        ImageView buyButton = view.findViewById(R.id.menuItemBuy);
        ImageView deleteButton = view.findViewById(R.id.menuItemDelete);

        name.setText(dish.getName());
        desc.setText(dish.getDescription());
        price.setText(""+dish.getPrice());

        if(SessionStorage.isAdmin){
            buyButton.setVisibility(View.GONE);
            deleteButton.setOnClickListener(v -> {
                new RemoveDishRequest(getContext(), this, position).execute(""+dish.getId());
            });
        }else{
            deleteButton.setVisibility(View.GONE);
            buyButton.setOnClickListener(v -> {

            });
        }

        return view;
    }

    public void removeId(int adapterId){
        remove(getItem(adapterId));
    }
}
