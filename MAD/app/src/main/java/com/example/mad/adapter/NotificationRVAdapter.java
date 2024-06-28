package com.example.mad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.model.Invite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.NotificationViewHolder> {
    private Context mContext;
    private ArrayList<Invite> invites;

    private onItemClickListener mListener;

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public NotificationRVAdapter(Context context, ArrayList<Invite> notifications) {
        mContext = context;
        invites = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Invite invite = invites.get(position);

        holder.titleTextView.setText("Thông báo lúc: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(invite.getCreatedDate()));
        holder.messageTextView.setText(invite.getContext());
        if (invite.isRead() == true) {
            holder.iconImageView.setVisibility(View.GONE);
        } else {
            holder.iconImageView.setVisibility(View.VISIBLE);
        }
        System.out.println(invite);

        holder.cardviewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView messageTextView;
        public ImageView iconImageView;
        public RelativeLayout cardviewNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txtViewNotificationTitle);
            messageTextView = itemView.findViewById(R.id.txtViewNotificationMessage);
            iconImageView = itemView.findViewById(R.id.imgViewNotificationIcon);
            cardviewNotification = itemView.findViewById(R.id.cardviewNotification);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
