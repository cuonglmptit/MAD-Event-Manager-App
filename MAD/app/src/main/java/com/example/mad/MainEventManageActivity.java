package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mad.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

public class MainEventManageActivity extends AppCompatActivity {

    private ImageButton btnSorryOption, btnQuick, btnNotifiOption, btnInviteCreate, btnInviteSent;

    private Event currentEvenWorkingOn;

    private Toolbar toolBarMainEventManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event_manage);

        Intent intent = getIntent();
        currentEvenWorkingOn = (Event) intent.getSerializableExtra("event");
        System.out.println("Intent event gia tri: " + currentEvenWorkingOn);
        initInstance();
        setupButtons();
        setupEventTitle(currentEvenWorkingOn);
    }

    private void setupButtons() {
        btnInviteSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Invited guests " + currentEvenWorkingOn.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), AcceptedUsersActivity.class);
                intent.putExtra("event", currentEvenWorkingOn);
                startActivity(intent);
            }
        });

        btnInviteCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Create invite " + currentEvenWorkingOn.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), CreateInviteActivity.class);
                intent.putExtra("event", currentEvenWorkingOn);
                startActivity(intent);
            }
        });

        btnQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
                builder.setView(dialogView);

                TextView txtViewDialogTitle = dialogView.findViewById(R.id.txtViewDialogTitle);
                TextView txtViewDialogMessage = dialogView.findViewById(R.id.txtViewDialogMessage);
                Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
                Button btnDialogAccept = dialogView.findViewById(R.id.btnDialogAccept);

                txtViewDialogTitle.setText("Mời lại nhanh");
                txtViewDialogMessage.setText("Lựa chọn này sẽ gửi thông báo thay đổi về lịch trình" +
                        " đến những người đã xác nhận tham gia sự kiện này" +
                        " (chỉ nên gửi khi có sự thay đổi về lịch trình). Bạn có chắc chắn muốn tiếp tục?");

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
                        sendQuickInvite();
                        alertDialog.dismiss(); // Dismiss the dialog after performing the action
                    }
                });

                alertDialog.show();
            }
        });

        btnSorryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_sorry, null);
                builder.setView(dialogView);

                TextView txtViewDialogTitle = dialogView.findViewById(R.id.txtViewDialogTitleSr);
                Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancelSr);
                Button btnDialogAccept = dialogView.findViewById(R.id.btnDialogAcceptSr);
                EditText edtTxtSorryContext = dialogView.findViewById(R.id.edtTxtSorryContext);
                txtViewDialogTitle.setText("Gửi lời xin lỗi!");

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
                        if (edtTxtSorryContext.getText().toString().isEmpty()) {
                            Toast.makeText(v.getContext(), "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sendSorry(edtTxtSorryContext.getText().toString());
                        alertDialog.dismiss(); // Dismiss the dialog after performing the action
                    }
                });

                alertDialog.show();
            }
        });
    }

    private void sendSorry(String context) {
        String url = "http://10.0.2.2:8080/invite/send-sorry/" + currentEvenWorkingOn.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(MainEventManageActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý phản hồi JSON từ server ở đây
                        try {
                            String message = response.getString("message");
                            Toast.makeText(MainEventManageActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi ở đây nếu cần
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String jsonString = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                        JSONObject jsonObject = new JSONObject(jsonString);

                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(MainEventManageActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                    Toast.makeText(MainEventManageActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
                Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                // Chuyển đổi dữ liệu text thành mảng byte và trả về
                return context.getBytes();
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void sendQuickInvite() {
        String url = "http://10.0.2.2:8080/invite/send-quick-invite/" + currentEvenWorkingOn.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(MainEventManageActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(MainEventManageActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                JSONObject jsonObject = new JSONObject(jsonString);

                                String errorMessage = jsonObject.getString("message");
                                Toast.makeText(MainEventManageActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                            Toast.makeText(MainEventManageActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void initInstance() {
        btnSorryOption = findViewById(R.id.btnSorryOption);
        btnQuick = findViewById(R.id.btnQuick);
        btnNotifiOption = findViewById(R.id.btnNotifiOption);
        btnInviteCreate = findViewById(R.id.btnInviteCreate);
        btnInviteSent = findViewById(R.id.btnInviteSent);

        toolBarMainEventManage = findViewById(R.id.toolBarMainEventManage);
        setSupportActionBar(toolBarMainEventManage);
        setTitle("Quản lý khách mời");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarMainEventManage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void setupEventTitle(Event event) {
        ImageView imgViewEventCover = findViewById(R.id.imgViewEventCover);
        Glide.with(this).load(event.getImageUrl()).into(imgViewEventCover);
        TextView eventFeedbackName = findViewById(R.id.eventFeedbackName);
        TextView eventFeedbackTime = findViewById(R.id.eventFeedbackTime);
        eventFeedbackName.setText(event.getName());
        eventFeedbackTime.setText(new SimpleDateFormat("EEEE, dd MM yyyy").format(event.getStartDate()) + " at " + event.getStartTime());

        RequestQueue requestQueue = Volley.newRequestQueue(MainEventManageActivity.this);
        String url = "http://10.0.2.2:8080/invite/get-invite-accepted-user-of-event/" + currentEvenWorkingOn.getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int numberOfInvitedUsers = response.getInt("data");
                            Log.d("NUMBER_OF_INVITED_USERS", "Số lượng người đã được mời: " + numberOfInvitedUsers);
                            TextView txtViewAcceptedUser = findViewById(R.id.txtViewAcceptedUser);
                            txtViewAcceptedUser.setText("Số lượng xác nhận tham gia: " + String.valueOf(numberOfInvitedUsers));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY_ERROR", "Error: " + error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

}