package com.example.mad.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad.MainEventManageActivity;
import com.example.mad.MainFeedbackActivity;
import com.example.mad.R;
import com.example.mad.adapter.EventRecyclerViewAdapter;
import com.example.mad.model.Event;
import com.example.mad.retrofit.EventAPI;
import com.example.mad.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentManage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentManage newInstance(String param1, String param2) {
        FragmentManage fragment = new FragmentManage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }


    private RecyclerView recyclerViewManageEvents;
    private ImageButton btnEventFeedback;
    private EditText edtTxtSearchEventManage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fetchEvents();
    }

    private void initViews(View view) {
        recyclerViewManageEvents = view.findViewById(R.id.recyclerViewManageEvents);
        edtTxtSearchEventManage = view.findViewById(R.id.edtTxtSearchEventManage);
    }

    private void fetchEvents() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("login_status", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userId", 0);
        RetrofitService retrofitService = new RetrofitService();
        EventAPI eventAPI = retrofitService.getRetrofit().create(EventAPI.class);
        eventAPI.getAllEventsByUserID(userID).enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Event> events = response.body();
                    setupEventRecyclerView(events);
                    setupSearch(events);
                } else {
                    Log.e("Fetch Events", "Failed: " + response.message());
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.e("Fetch Events", "Error: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearch(ArrayList<Event> events) {
        edtTxtSearchEventManage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Event> filteredEvents = filterEventByNameAndID(events, s);
                setupEventRecyclerView(filteredEvents);
            }
        });
    }

    private ArrayList<Event> filterEventByNameAndID(ArrayList<Event> events, Editable s) {
        String text = s.toString();
        if (text.isEmpty()) {
            return events;
        }
        ArrayList<Event> filtered = new ArrayList<>();
        text = text.toLowerCase();
        for (Event event : events) {
            try {
                if (event.getName().toLowerCase().contains(text)) {
                    filtered.add(event);
                } else if (event.getId() == Integer.parseInt(text)) {
                    filtered.add(event);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filtered;
    }

    private void setupEventRecyclerView(ArrayList<Event> events) {
        EventRecyclerViewAdapter eventAdapter = new EventRecyclerViewAdapter(requireContext(), events);
        recyclerViewManageEvents.setAdapter(eventAdapter);
        recyclerViewManageEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventAdapter.setOnButtonClickListener(new EventRecyclerViewAdapter.OnButtonClickListener() {
            @Override
            public void onScheduleClick(View view, int position) {
                // Handle schedule click
            }

            @Override
            public void onTaskClick(View view, int position) {
                // Handle task click
            }

            @Override
            public void onGuestsClick(View view, int position) {
                Toast.makeText(requireContext(), "Manage Guests " + events.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), MainEventManageActivity.class);
                intent.putExtra("event", events.get(position));
                startActivity(intent);
            }

            @Override
            public void onMoreClick(View view, int position) {
                final Dialog dialogMore = new Dialog(requireContext());
                dialogMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMore.setContentView(R.layout.dialog_more_options);
                Window window = dialogMore.getWindow();
                if (window == null) return;

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);

                TextView txtViewMoreOptions = dialogMore.findViewById(R.id.txtViewMoreOptions);
                txtViewMoreOptions.setText(txtViewMoreOptions.getText() + " of\n" + events.get(position).getName());
                dialogMore.setCancelable(true);
                setupDialogMoreButtons(dialogMore, events.get(position));
                dialogMore.show();
            }

        });


    }

    private void setupDialogMoreButtons(Dialog dialogMore, Event event) {
        btnEventFeedback = dialogMore.findViewById(R.id.btnEventFeedback);
        btnEventFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Vào feedback", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), MainFeedbackActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });
    }


}