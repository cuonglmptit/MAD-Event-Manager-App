package com.example.mad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mad.adapter.UserAddedInviteAdapter;
import com.example.mad.model.Event;
import com.example.mad.model.InviteDTO;
import com.example.mad.model.InviteStatus;
import com.example.mad.model.User;
import com.example.mad.retrofit.RetrofitService;
import com.example.mad.retrofit.UserAPI;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateInviteActivity extends AppCompatActivity {

    private RelativeLayout manageDetailParent;
    private Toolbar toolBarCreateInvite;
    private RelativeLayout eventImageLayout;
    private ImageView imgViewEventCover;
    private TextView eventFeedbackName;
    private TextView eventFeedbackTime;
    private RelativeLayout fieldLayout;
    private TextInputEditText textInputAddUser;
    private ImageButton addInviteUserButton;
    private TextView txtViewAddedUser;
    private RecyclerView addedUserRecyclerView;
    private Button btnSendInvite;
    private Event currentEvenWorkingOn;
    private ArrayList<User> addedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invite);
        Intent intent = getIntent();
        currentEvenWorkingOn = (Event) intent.getSerializableExtra("event");
        System.out.println("Intent event gia tri: " + currentEvenWorkingOn);

        initInstances();
        setupEventTitle(currentEvenWorkingOn);
        setupButtons();
    }

    private void setupEventTitle(Event event) {
        ImageView imgViewEventCover = findViewById(R.id.imgViewEventCover);
        Glide.with(this).load(event.getImageUrl()).into(imgViewEventCover);
        TextView eventFeedbackName = findViewById(R.id.eventFeedbackName);
        TextView eventFeedbackTime = findViewById(R.id.eventFeedbackTime);
        eventFeedbackName.setText(event.getName());
        eventFeedbackTime.setText(new SimpleDateFormat("EEEE, dd MM yyyy").format(event.getStartDate()) + " at " + event.getStartTime());
    }

    private void initInstances() {
        toolBarCreateInvite = findViewById(R.id.toolBarCreateInvite);
        setSupportActionBar(toolBarCreateInvite);
        setTitle("Tạo lời mời");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarCreateInvite.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        manageDetailParent = findViewById(R.id.manageDetailParent);
        eventImageLayout = findViewById(R.id.eventImageLayout);
        imgViewEventCover = findViewById(R.id.imgViewEventCover);
        eventFeedbackName = findViewById(R.id.eventFeedbackName);
        eventFeedbackTime = findViewById(R.id.eventFeedbackTime);
        fieldLayout = findViewById(R.id.fieldLayout);
        textInputAddUser = findViewById(R.id.textInputAddUser);
        addInviteUserButton = findViewById(R.id.addInviteUserButton);
        txtViewAddedUser = findViewById(R.id.txtViewAddedUser);
        txtViewAddedUser.setText(txtViewAddedUser.getText() + "0 người");
        addedUserRecyclerView = findViewById(R.id.addedUserRecyclerView);
        btnSendInvite = findViewById(R.id.btnSendInvite);

        addedUsers = new ArrayList<>();
    }

    private void setupAddedUserRecyclerV(ArrayList<User> users) {
        txtViewAddedUser.setText("Đã thêm: " + addedUsers.size() + " người");
        UserAddedInviteAdapter adapter = new UserAddedInviteAdapter(this, users);
        addedUserRecyclerView.setAdapter(adapter);
        addedUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnDeleteUserClickListener(new UserAddedInviteAdapter.OnDeleteUserClickListener() {
            @Override
            public void onDeleteUserClick(int position) {
                User user = addedUsers.get(position);
                addedUsers.remove(position);
                Toast.makeText(CreateInviteActivity.this, "Đã xóa " + user.getName(), Toast.LENGTH_SHORT).show();
                setupAddedUserRecyclerV(users);
            }
        });
    }

    private void setupButtons() {
        addInviteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = (String) textInputAddUser.getText().toString();

                if (input.isEmpty()) {
                    Toast.makeText(CreateInviteActivity.this, "Bạn chưa nhập user nào!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RetrofitService retrofitService = new RetrofitService();
                UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);

                userAPI.getUser(input).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if (user != null) {
                            for (User addedUser : addedUsers) {
                                if (addedUser.getId() == user.getId()) {
                                    Toast.makeText(CreateInviteActivity.this, "Bạn đã thêm user này trước đó rồi!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            System.out.println("Call get user: " + user);
                            addedUsers.add(user);
                            setupAddedUserRecyclerV(addedUsers);
                            Toast.makeText(CreateInviteActivity.this, "Thêm thành công user: " + user.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(CreateInviteActivity.this, "Thêm không thành công, không tồn tại user!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addedUsers.size() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
                    builder.setView(dialogView);

                    TextView txtViewDialogTitle = dialogView.findViewById(R.id.txtViewDialogTitle);
                    TextView txtViewDialogMessage = dialogView.findViewById(R.id.txtViewDialogMessage);
                    Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
                    Button btnDialogAccept = dialogView.findViewById(R.id.btnDialogAccept);

                    txtViewDialogTitle.setText("Xác nhận");
                    txtViewDialogMessage.setText("Bạn có chắc muốn gửi lời mời đến " + addedUsers.size() + " người?");

                    AlertDialog alertDialog = builder.create();

                    btnDialogCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    btnDialogAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendInvite();
                            alertDialog.dismiss(); // Dismiss the dialog after performing the action
                        }
                    });

                    alertDialog.show();
                } else {
                    Toast.makeText(CreateInviteActivity.this, "Bạn chưa thêm ai để có thể thực hiện lời mời", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendInvite() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(CreateInviteActivity.this);
            String url = "http://10.0.2.2:8080/invite/post";
            SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
            AtomicInteger succeeded = new AtomicInteger(0);
            AtomicInteger failed = new AtomicInteger(0);
            AtomicInteger totalRequests = new AtomicInteger(addedUsers.size());

            for (User user : addedUsers) {
                InviteDTO inviteDTO = new InviteDTO();
                inviteDTO.setInviteStatus(InviteStatus.PENDING);
                inviteDTO.setToUser(user.getId());
                inviteDTO.setEventId(currentEvenWorkingOn.getId());
                inviteDTO.setContext("Người dùng " + sharedPreferences.getString("name", "")
                        + " gửi lời mời cho bạn đến " + currentEvenWorkingOn.getName() + " EventID: " + currentEvenWorkingOn.getId()
                        + " sẽ bắt đầu vào ngày " + new SimpleDateFormat("EEEE, dd MM yyyy").format(currentEvenWorkingOn.getStartDate())
                        + " lúc " + currentEvenWorkingOn.getStartTime()
                        + " tại " + currentEvenWorkingOn.getAddress());

                // Tạo một HashMap chứa thông tin của yêu cầu POST
                HashMap<String, String> params = inviteDTO.toHashMap();

                // Gửi yêu cầu POST
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        succeeded.incrementAndGet();
                        checkSentAndShowToast(succeeded, failed, totalRequests);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        failed.incrementAndGet();
                        checkSentAndShowToast(succeeded, failed, totalRequests);
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        } catch (Exception e) {
            // Xử lý lỗi
            e.printStackTrace();
            // Hiển thị thông báo lỗi cho người dùng
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSentAndShowToast(AtomicInteger succeeded, AtomicInteger failed, AtomicInteger totalRequests) {
        if (totalRequests.get() == succeeded.get() + failed.get()) {
            String toastMessage = "Gửi lời mời thành công đến: " + succeeded.get() + " người";
            if (failed.get() > 0) {
                toastMessage += " và thất bại: " + failed.get() + " người";
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }


}