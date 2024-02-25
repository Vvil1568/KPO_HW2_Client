package com.vvi.restaurantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vvi.restaurantapp.R;
import com.vvi.restaurantapp.items.Comment;

public class CommentListAdapter extends ArrayAdapter<Comment> {
    private int itemResourceType;

    public CommentListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemResourceType = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comment comment = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (convertView == null) {
            view = inflater.inflate(itemResourceType, parent, false);
        } else {
            view = convertView;
        }
        TextView name = view.findViewById(R.id.commentDishName);
        RatingBar bar = view.findViewById(R.id.commentRatingBar);
        TextView commentText = view.findViewById(R.id.feedbackText);

        name.setText(comment.getName());
        bar.setNumStars(comment.getStars());
        commentText.setText(comment.getComment());
        return view;
    }

    public void removeId(int adapterId) {
        remove(getItem(adapterId));
    }
}