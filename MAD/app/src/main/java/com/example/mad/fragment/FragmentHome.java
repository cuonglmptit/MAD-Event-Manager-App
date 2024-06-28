package com.example.mad.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mad.HomeEventFeedback;
import com.example.mad.NotificationActivity;
import com.example.mad.R;
import com.example.mad.adapter.EventRVAdapterHome;
import com.example.mad.model.Event;
import com.example.mad.retrofit.EventAPI;
import com.example.mad.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private RecyclerView recyclerViewHomeEvents;
    private EditText edtTxtSearchEventHome;
    private ImageButton btnNotification;

    int userId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("login_status", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        fetchEvents();
        setUpNotificationButton(view);
    }

    private void setUpNotificationButton(View view) {
        ImageButton notifiButton = view.findViewById(R.id.btnNotification);


        notifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        String url = "http://10.0.2.2:8080/invite/get-have-unread-invite/" + userId;
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý phản hồi JSON từ server ở đây
                        try {
                            boolean data = response.getBoolean("data");
                            if (data) {
                                notifiButton.setImageResource(R.drawable.noti_alert);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi ở đây nếu cần
                Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                // Chuyển đổi dữ liệu text thành mảng byte và trả về
                return "read all user".getBytes();
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchEvents();
        setUpNotificationButton(getView());
    }

    private void initViews(View view) {
        recyclerViewHomeEvents = view.findViewById(R.id.recyclerViewHomeEvents);
        edtTxtSearchEventHome = view.findViewById(R.id.edtTxtSearchEventHome);
        btnNotification = view.findViewById(R.id.btnNotification);
    }

    private void fetchEvents() {
        RetrofitService retrofitService = new RetrofitService();
        EventAPI eventAPI = retrofitService.getRetrofit().create(EventAPI.class);
        eventAPI.getAllEvents().enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Event> events = response.body();
                    setupEventHomeRecyclerView(events);
                    setupSearchHome(events);
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

    private void setupSearchHome(ArrayList<Event> events) {
        edtTxtSearchEventHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Event> filteredEvents = filterEventByNameAndID(events, s);
                setupEventHomeRecyclerView(filteredEvents);
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


    private void setupEventHomeRecyclerView(ArrayList<Event> events) {
        EventRVAdapterHome eventAdapter = new EventRVAdapterHome(requireContext(), events);
        recyclerViewHomeEvents.setAdapter(eventAdapter);
        recyclerViewHomeEvents.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        eventAdapter.setOnEventClickListener(new EventRVAdapterHome.OnEventClickListener() {
            @Override
            public void onEventClick(View view, int position) {
                Intent intent = new Intent(requireContext(), HomeEventFeedback.class);
                intent.putExtra("event", events.get(position));
                startActivity(intent);
            }
        });
    }
}