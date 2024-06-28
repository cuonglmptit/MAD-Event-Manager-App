package com.example.mad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mad.R;
import com.example.mad.model.FeedbackType;

import java.util.ArrayList;
import java.util.List;

public class FeedbackTypeAdapter extends ArrayAdapter<FeedbackType>{

    public FeedbackTypeAdapter(@NonNull Context context, int resource, @NonNull List<FeedbackType> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_feedback_selected, parent, false);
        TextView txtViewFeedbackTypeSelected = convertView.findViewById(R.id.txtViewFeedbackTypeSelected);
        ImageView imgViewFeedbackTypeSelected = convertView.findViewById(R.id.imgViewFeedbackTypeSelected);

        FeedbackType feedbackType = this.getItem(position);
        if(feedbackType != null){
            txtViewFeedbackTypeSelected.setText(feedbackType.getDisplayName());
            imgViewFeedbackTypeSelected.setImageResource(feedbackType.getImage());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_feedback, parent, false);
        TextView txtViewFeedbackType = convertView.findViewById(R.id.txtViewFeedbackType);
        ImageView imgViewFeedbackType = convertView.findViewById(R.id.imgViewFeedbackType);

        FeedbackType feedbackType = this.getItem(position);
        if(feedbackType != null){
            txtViewFeedbackType.setText(feedbackType.getDisplayName());
            imgViewFeedbackType.setImageResource(feedbackType.getImage());
        }
        return convertView;
    }

}
