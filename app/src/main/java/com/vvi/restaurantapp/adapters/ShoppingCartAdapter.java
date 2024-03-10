package com.vvi.restaurantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.activities.ShoppingCartActivity;
import com.vvi.restaurantapp.items.Dish;
import com.vvi.restaurantapp.requests.order.ChangeDishCountRequest;

import java.util.AbstractMap.SimpleEntry;

public class ShoppingCartAdapter extends ArrayAdapter<SimpleEntry<Dish, Integer>> {
    private final int itemResourceType;
    private final ShoppingCartActivity activity;

    public ShoppingCartAdapter(@NonNull ShoppingCartActivity context, int resource) {
        super(context, resource);
        this.itemResourceType = resource;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SimpleEntry<Dish, Integer> item = this.getItem(position);
        Dish dish = item.getKey();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (convertView == null) {
            view = inflater.inflate(itemResourceType, parent, false);
        } else {
            view = convertView;
        }
        TextView name = view.findViewById(R.id.cartItemName);
        TextView price = view.findViewById(R.id.cartItemPrice);
        TextView count = view.findViewById(R.id.cartItemCount);
        TextView incrementButton = view.findViewById(R.id.cartIncrement);
        TextView decrementButton = view.findViewById(R.id.cartDecrement);

        name.setText(dish.getName());
        price.setText("" + dish.getPrice());
        count.setText("" + item.getValue());

        incrementButton.setOnClickListener(v -> {
            new ChangeDishCountRequest(activity, this, position).execute("" + dish.getId(), "" + 1);
        });

        decrementButton.setOnClickListener(v -> {
            new ChangeDishCountRequest(activity, this, position).execute("" + dish.getId(), "" + (-1));
        });

        return view;
    }

    public void removeId(int adapterId) {
        remove(getItem(adapterId));
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        double total = 0;
        for (int i = 0; i < getCount(); i++) {
            SimpleEntry<Dish, Integer> item = getItem(i);
            total += item.getKey().getPrice() * item.getValue();
        }
        ((TextView) activity.findViewById(R.id.cartPrice)).setText("" + total);
    }
}
