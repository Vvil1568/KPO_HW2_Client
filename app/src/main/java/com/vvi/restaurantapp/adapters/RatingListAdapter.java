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

import java.util.AbstractMap;

public class RatingListAdapter extends ArrayAdapter<AbstractMap.SimpleEntry<String, Double>> {
    private final int itemResourceType;

    public RatingListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemResourceType = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AbstractMap.SimpleEntry<String, Double> rating = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (convertView == null) {
            view = inflater.inflate(itemResourceType, parent, false);
        } else {
            view = convertView;
        }
        TextView name = view.findViewById(R.id.ratingItemName);
        TextView rate = view.findViewById(R.id.ratingItemRate);

        name.setText(rating.getKey());
        rate.setText(String.format("%.2f",rating.getValue()));
        return view;
    }

    public void removeId(int adapterId) {
        remove(getItem(adapterId));
    }
}