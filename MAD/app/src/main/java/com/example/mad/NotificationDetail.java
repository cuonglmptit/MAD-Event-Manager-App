package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mad.model.Event;
import com.example.mad.model.Invite;
import com.example.mad.model.InviteStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class NotificationDetail extends AppCompatActivity {

    Invite currentInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Intent intent = getIntent();
        currentInvite = (Invite) intent.getSerializableExtra("invite");
        putReadInvite();
        initInstances();
    }

    private void putReadInvite() {
        String url = "http://10.0.2.2:8080/invite/read/" + currentInvite.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(NotificationDetail.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý phản hồi JSON từ server ở đây
                        try {
                            String message = response.getString("message");
                            Toast.makeText(NotificationDetail.this, message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NotificationDetail.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                    Toast.makeText(NotificationDetail.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
                Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                // Chuyển đổi dữ liệu text thành mảng byte và trả về
                return "read invite".getBytes();
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void initInstances() {
        Toolbar toolbar = findViewById(R.id.toolBarNotificationInviteDetail);
        setSupportActionBar(toolbar);
        setTitle("Chi tiết thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        TextView txtViewNotificationDetail = findViewById(R.id.txtViewNotificationDetail);
        txtViewNotificationDetail.setText(currentInvite.getContext());

        LinearLayout confirmLayout = findViewById(R.id.confirmLayout);

        if (currentInvite.getInviteStatus().equals(InviteStatus.PENDING)) {
            confirmLayout.setVisibility(View.VISIBLE);
            Button btnCancel = findViewById(R.id.btnDialogCancelInvtDetail);
            Button btnOk = findViewById(R.id.btnDialogAcceptInvtDetail);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://10.0.2.2:8080/invite/accept/" + currentInvite.getId();
                    RequestQueue requestQueue = Volley.newRequestQueue(NotificationDetail.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                            new com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Xử lý phản hồi JSON từ server ở đây
                                    try {
                                        String message = response.getString("message");
                                        confirmLayout.setVisibility(View.GONE);
                                        Toast.makeText(NotificationDetail.this, message, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(NotificationDetail.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                                Toast.makeText(NotificationDetail.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://10.0.2.2:8080/invite/decline/" + currentInvite.getId();
                    RequestQueue requestQueue = Volley.newRequestQueue(NotificationDetail.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                            new com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Xử lý phản hồi JSON từ server ở đây
                                    try {
                                        String message = response.getString("message");
                                        confirmLayout.setVisibility(View.GONE);
                                        Toast.makeText(NotificationDetail.this, message, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(NotificationDetail.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                                Toast.makeText(NotificationDetail.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            });
        } else {
            confirmLayout.setVisibility(View.GONE);
        }
    }
}