package com.example.mad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mad.R;
import com.example.mad.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventRVAdapterHome extends RecyclerView.Adapter<EventRVAdapterHome.EventViewHolderHome>{
    Context context;
    ArrayList<Event> events;
    private EventRVAdapterHome.OnEventClickListener listener;

    public void setOnEventClickListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    public EventRVAdapterHome(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventRVAdapterHome.EventViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate cac layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_card_view_home, parent, false);
        return new EventRVAdapterHome.EventViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRVAdapterHome.EventViewHolderHome holder, int position) {
        //Gán giá trị các dòng
        Event curentEvent = events.get(position);
        Glide.with(context).load(curentEvent.getImageUrl()).into(holder.imgViewEventCover);
        holder.txtViewEventTitle.setText(curentEvent.getName());
        holder.txtViewEventTime.setText(new SimpleDateFormat("EEEE, dd MM yyyy").format(curentEvent.getStartDate()) + " at " + curentEvent.getStartTime());
        holder.txtViewEventDesc.setText(curentEvent.getDescription());

        holder.eventCardViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onEventClick(v, holder.getAdapterPosition());
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

    public class EventViewHolderHome extends RecyclerView.ViewHolder {
        //Lấy layout xml và gán
        //Khá giống onCreate

        ImageView imgViewEventCover;
        TextView txtViewEventTitle, txtViewEventTime, txtViewEventDesc;
        CardView eventCardViewHome;
        public EventViewHolderHome(@NonNull View itemView) {
            super(itemView);
            imgViewEventCover = itemView.findViewById(R.id.imgViewEventCoverHome);
            txtViewEventTitle = itemView.findViewById(R.id.txtViewEventTitleHome);
            txtViewEventTime = itemView.findViewById(R.id.txtViewEventTimeHome);
            txtViewEventDesc = itemView.findViewById(R.id.txtViewEventDescHome);

            eventCardViewHome = itemView.findViewById(R.id.eventCardViewHome);
        }
    }

    public interface OnEventClickListener {
        void onEventClick(View view, int position);
    }
}
