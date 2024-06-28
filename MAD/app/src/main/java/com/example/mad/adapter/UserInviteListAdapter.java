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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserInviteListAdapter extends RecyclerView.Adapter<UserInviteListAdapter.UserInviteViewHolder> {

    Context context;
    ArrayList<Invite> invites;

    public UserInviteListAdapter(Context context, ArrayList<Invite> invites) {
        this.context = context;
        this.invites = invites;
    }

    @NonNull
    @Override
    public UserInviteListAdapter.UserInviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_user_invite, parent, false);
        return new UserInviteListAdapter.UserInviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInviteListAdapter.UserInviteViewHolder holder, int position) {
        Invite currentInvite = invites.get(position);
        holder.txtViewUserInviteName.setText(currentInvite.getToUser().getName());
        holder.txtListIndex.setText(String.valueOf(position + 1));
        holder.txtViewInviteDate.setText("Sent: "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentInvite.getCreatedDate()));
        holder.btnDeleteUser.setVisibility(View.GONE);
        if (currentInvite.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
            holder.imgViewInviteStatus.setImageResource(R.drawable.accept_mark);
        } else if (currentInvite.getInviteStatus().equals(InviteStatus.PENDING)) {
            holder.imgViewInviteStatus.setImageResource(R.drawable.pending);
        } else {
            holder.imgViewInviteStatus.setImageResource(R.drawable.declined);
        }

    }

    @Override
    public int getItemCount() {
        if (invites != null) {
            return invites.size();
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
}
