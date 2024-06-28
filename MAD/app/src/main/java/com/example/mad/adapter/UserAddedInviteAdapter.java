package com.example.mad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.model.Invite;
import com.example.mad.model.InviteStatus;
import com.example.mad.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserAddedInviteAdapter extends RecyclerView.Adapter<UserAddedInviteAdapter.UserInviteViewHolder> {

    Context context;
    ArrayList<User> users;
    private OnDeleteUserClickListener onDeleteUserClickListener;

    // Method to set the listener
    public void setOnDeleteUserClickListener(OnDeleteUserClickListener listener) {
        this.onDeleteUserClickListener = listener;
    }

    public UserAddedInviteAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAddedInviteAdapter.UserInviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_user_invite, parent, false);
        return new UserAddedInviteAdapter.UserInviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAddedInviteAdapter.UserInviteViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtViewUserInviteName.setText(user.getName());
        holder.txtListIndex.setText(String.valueOf(position + 1));
        holder.imgViewInviteStatus.setImageResource(0);
        holder.txtViewInviteDate.setText("");
        holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (onDeleteUserClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    onDeleteUserClickListener.onDeleteUserClick(clickedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        }
        return 0;
    }

    public class UserInviteViewHolder extends RecyclerView.ViewHolder {

        ImageView imgViewInviteStatus;
        TextView txtListIndex, txtViewUserInviteName, txtViewInviteDate;
        ImageButton btnDeleteUser;

        public UserInviteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewInviteStatus = itemView.findViewById(R.id.imgViewInviteStatus);
            txtListIndex = itemView.findViewById(R.id.txtListIndex);
            txtViewUserInviteName = itemView.findViewById(R.id.txtViewUserInviteName);
            txtViewInviteDate = itemView.findViewById(R.id.txtViewInviteDate);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }
    }

    public interface OnDeleteUserClickListener {
        void onDeleteUserClick(int position);
    }
}
