package com.example.mad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.model.Feedback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FeedbackRVAdapter extends RecyclerView.Adapter<FeedbackRVAdapter.FeedbackViewHolder> {
    Context context;
    ArrayList<Feedback> feedbacks;

    public FeedbackRVAdapter(Context context, ArrayList<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @NonNull
    @Override
    public FeedbackRVAdapter.FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_feedback, parent, false);
        return new FeedbackRVAdapter.FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackRVAdapter.FeedbackViewHolder holder, int position) {
            Feedback currentFeedback = feedbacks.get(position);
            holder.imgViewAFeedbackType.setImageResource(currentFeedback.getType().getImage());
            holder.txtUserName.setText(currentFeedback.getUser().getName());
            holder.txtViewFeedbackMessage.setText(currentFeedback.getMessage());
            holder.txtViewFeedbackCreatedTime.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(currentFeedback.getCreatedTime()));
    }

    @Override
    public int getItemCount() {
        if (feedbacks != null)
            return feedbacks.size();
        return 0;
    }


    public class FeedbackViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewAFeedbackType;
        TextView txtViewFeedbackMessage, txtUserName, txtViewFeedbackCreatedTime;
        public FeedbackViewHolder(@NonNull View itemView) {

            super(itemView);
            imgViewAFeedbackType = itemView.findViewById(R.id.imgViewFeedbackTypeOfUser);
            txtUserName = itemView.findViewById(R.id.txtFeedbackUserName);
            txtViewFeedbackMessage = itemView.findViewById(R.id.txtViewFeedbackMessage);
            txtViewFeedbackCreatedTime = itemView.findViewById(R.id.txtViewFeedbackCreatedTime);
        }
    }
}
