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

import com.bumptech.glide.Glide;
import com.example.mad.R;
import com.example.mad.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.EventViewHolder> {

    Context context;
    ArrayList<Event> events;
    private OnButtonClickListener listener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    public EventRecyclerViewAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventRecyclerViewAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate cac layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_event, parent, false);
        return new EventRecyclerViewAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewAdapter.EventViewHolder holder, int position) {
        //Gán giá trị các dòng
        Event curentEvent = events.get(position);
        Glide.with(context).load(curentEvent.getImageUrl()).into(holder.imgViewEventCover);
        holder.txtViewEventTitle.setText(curentEvent.getName());
        holder.txtViewEventTime.setText(new SimpleDateFormat("EEEE, dd MM yyyy").format(curentEvent.getStartDate()) + " at " + curentEvent.getStartTime());

        holder.btnEventGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onGuestsClick(view, holder.getAdapterPosition());
                }
            }
        });
        holder.btnEventMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onMoreClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //Trả về số lượng item
        if (events != null)
            return events.size();
        return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        //Lấy layout xml và gán
        //Khá giống onCreate


        ImageView imgViewEventCover;
        TextView txtViewEventTitle, txtViewEventTime;
        ImageButton btnEventSchedule, btnEventask, btnEventGuests, btnEventMore;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewEventCover = itemView.findViewById(R.id.imgViewEventCoverHome);
            txtViewEventTitle = itemView.findViewById(R.id.txtViewEventTitleHome);
            txtViewEventTime = itemView.findViewById(R.id.txtViewEventTimeHome);

            // Khởi tạo các nút
            btnEventSchedule = itemView.findViewById(R.id.btnEventSchedule);
            btnEventask = itemView.findViewById(R.id.btnEventask);
            btnEventGuests = itemView.findViewById(R.id.btnEventGuests);
            btnEventMore = itemView.findViewById(R.id.btnEventMore);
        }
    }

    public interface OnButtonClickListener {
        void onScheduleClick(View view, int position);
        void onTaskClick(View view, int position);
        void onGuestsClick(View view, int position);
        void onMoreClick(View view, int position);
    }
}
