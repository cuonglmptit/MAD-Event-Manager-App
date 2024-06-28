package com.example.mad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mad.adapter.NotificationRVAdapter;
import com.example.mad.model.Invite;
import com.example.mad.retrofit.RetrofitService;
import com.example.mad.retrofit.UserInviteAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        initInstances();
        fetchInvites();
    }

    private void fetchInvites() {
        RetrofitService retrofitService = new RetrofitService();
        UserInviteAPI userInviteAPI = retrofitService.getRetrofit().create(UserInviteAPI.class);
        System.out.println("User id: " + userId);
        userInviteAPI.getAllInviteOfUser(userId)
                .enqueue(new Callback<ArrayList<Invite>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Invite>> call, Response<ArrayList<Invite>> response) {
                        ArrayList<Invite> invites = response.body();
                        for (Invite invite : invites) {
                            System.out.println(invite);
                        }
                        setUpNotificationRecyclerView(invites);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Invite>> call, Throwable t) {
                        System.out.println("Loi get du lieu");
                        Toast.makeText(NotificationActivity.this, "loi get du lieu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchInvites();
    }
    private void setUpNotificationRecyclerView(ArrayList<Invite> invites) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotificationInvite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotificationRVAdapter adapter = new NotificationRVAdapter(this, invites);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NotificationRVAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NotificationActivity.this, NotificationDetail.class);
                intent.putExtra("invite", invites.get(position));
                startActivity(intent);
            }
        });
    }

    private void initInstances() {
        Toolbar toolbar = findViewById(R.id.toolBarNotificationInvite);
        setSupportActionBar(toolbar);
        setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        ImageButton markAllReadButton = findViewById(R.id.btnMmarkAllRead);
        markAllReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.0.2.2:8080/invite/read-all-of-user/" + userId;
                RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Xử lý phản hồi JSON từ server ở đây
                                try {
                                    String message = response.getString("message");
                                    fetchInvites();
                                    Toast.makeText(NotificationActivity.this, message, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi ở đây nếu cần
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                JSONObject jsonObject = new JSONObject(jsonString);

                                String errorMessage = jsonObject.getString("message");
                                Toast.makeText(NotificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                            Toast.makeText(NotificationActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
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
        });
    }

}