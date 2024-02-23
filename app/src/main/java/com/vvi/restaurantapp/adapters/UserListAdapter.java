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
import com.vvi.restaurantapp.items.User;
import com.vvi.restaurantapp.requests.user.ChangeModeRequest;

public class UserListAdapter extends ArrayAdapter<User> {
    private int itemResourceType;

    public UserListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemResourceType = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (convertView == null) {
            view = inflater.inflate(itemResourceType, parent, false);
        } else {
            view = convertView;
        }
        TextView login = view.findViewById(R.id.userListLogin);
        TextView fio = view.findViewById(R.id.userListFio);
        ImageView changeMode = view.findViewById(R.id.changeMode);

        login.setText(user.getLogin());
        fio.setText(user.getFio());
        if (user.isAdmin()) {
            changeMode.setImageResource(R.drawable.lock_red);
        } else {
            changeMode.setImageResource(R.drawable.key_green);
        }
        changeMode.setOnClickListener(v -> {
            new ChangeModeRequest(getContext(), this, position).execute(user.getLogin());
        });
        return view;
    }

    public void removeId(int adapterId) {
        remove(getItem(adapterId));
    }
}