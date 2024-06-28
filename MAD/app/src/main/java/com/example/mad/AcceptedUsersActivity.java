package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mad.adapter.UserInviteListAdapter;
import com.example.mad.model.Event;
import com.example.mad.model.Invite;
import com.example.mad.model.InviteStatus;
import com.example.mad.retrofit.RetrofitService;
import com.example.mad.retrofit.UserInviteAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedUsersActivity extends AppCompatActivity {

    private Toolbar toolBarUserAccepted;
    private TextView txtViewNumberAcceptedUser;
    private Event currentEvenWorkingOn;

    private Spinner spnUserInviteStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_users);
        Intent intent = getIntent();
        currentEvenWorkingOn = (Event) intent.getSerializableExtra("event");
        System.out.println("Intent event gia tri: " + currentEvenWorkingOn);

        initInstance();

        setupEventTitle(currentEvenWorkingOn);

        fetchUserInvite();
    }

    private void fetchUserInvite() {
        RetrofitService retrofitService = new RetrofitService();
        UserInviteAPI userInviteAPI = retrofitService.getRetrofit().create(UserInviteAPI.class);
        userInviteAPI.getAllInvitedUserOfEvent(currentEvenWorkingOn.getId()).enqueue(new Callback<ArrayList<Invite>>() {
            @Override
            public void onResponse(Call<ArrayList<Invite>> call, Response<ArrayList<Invite>> response) {
                ArrayList<Invite> invites = response.body();
                for (Invite invite : invites) {
                    System.out.println(invite);
                }
                setupUserInviteRecyclerView(invites);
                setupInviteStatusSpinner(invites);
            }

            @Override
            public void onFailure(Call<ArrayList<Invite>> call, Throwable t) {
                System.out.println("Loi get du lieu");
                Toast.makeText(AcceptedUsersActivity.this, "loi get du lieu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUserInviteRecyclerView(ArrayList<Invite> invites) {
        UserInviteListAdapter userInviteAdapter = new UserInviteListAdapter(this, invites);
        RecyclerView userInviteRecyclerView = findViewById(R.id.userInviteRecyclerView);
        userInviteRecyclerView.setAdapter(userInviteAdapter);
        userInviteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupInviteStatusSpinner(ArrayList<Invite> invites) {
        spnUserInviteStatus = findViewById(R.id.spnUserInviteStatus);
        InviteStatus[] inviteStatusValues = InviteStatus.values();
        ArrayAdapter<InviteStatus> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, inviteStatusValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUserInviteStatus.setAdapter(adapter);

        spnUserInviteStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InviteStatus status = (InviteStatus) parent.getItemAtPosition(position);
                ArrayList<Invite> filtered = filteredInvites(invites, status);
                txtViewNumberAcceptedUser.setText(filtered.size()+" Người " +status.getDisplayName());
                setupUserInviteRecyclerView(filtered);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private ArrayList<Invite> filteredInvites(ArrayList<Invite> invites, InviteStatus status){
        ArrayList<Invite> filtered = new ArrayList<>();
        if (status.equals(InviteStatus.ALL)) {
            setupUserInviteRecyclerView(invites);
            return invites;
        }
        for (Invite invite : invites) {
            if (invite.getInviteStatus().equals(status)){
                filtered.add(invite);
            }
        }
        return filtered;
    }


    private void setupEventTitle(Event event) {
        ImageView imgViewEventCover = findViewById(R.id.imgViewEventCover);
        Glide.with(this).load(event.getImageUrl()).into(imgViewEventCover);
        TextView eventFeedbackName = findViewById(R.id.eventFeedbackName);
        TextView eventFeedbackTime = findViewById(R.id.eventFeedbackTime);
        eventFeedbackName.setText(event.getName());
        eventFeedbackTime.setText(new SimpleDateFormat("EEEE, dd MM yyyy").format(event.getStartDate()) + " at " + event.getStartTime());
    }

    private void initInstance() {
        txtViewNumberAcceptedUser = findViewById(R.id.txtViewNumberAcceptedUser);
        toolBarUserAccepted = findViewById(R.id.toolBarCreateInvite);
        setSupportActionBar(toolBarUserAccepted);
        setTitle("Lời mời đã gửi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarUserAccepted.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}