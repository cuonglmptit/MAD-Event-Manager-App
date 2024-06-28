package com.example.mad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    private EditText edtxtInUsername, edtxtInPassword;
    private Button btnDangNhap;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        setupListeners();
    }

    private void initViews() {
        edtxtInUsername = findViewById(R.id.edtxtInUsername);
        edtxtInPassword = findViewById(R.id.edtxtInPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

    private void setupListeners() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    private boolean validateLoginForm() {
        String inUsername = edtxtInUsername.getText().toString();
        String inPassword = edtxtInPassword.getText().toString();

        if (inUsername.isEmpty() || inPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void authenticateUser() {
        if (validateLoginForm()) {
//            System.out.println("Vao duoc authenticateUser ");
            RequestQueue requestQueue = Volley.newRequestQueue(SignInActivity.this);
            // Thực hiện xác thực người dùng
            String url = "http://10.0.2.2:8080/user/login";

            HashMap<String, String> params = new HashMap<>();
            params.put("username", edtxtInUsername.getText().toString());
            params.put("password", edtxtInPassword.getText().toString());
//            System.out.println("Den duoc truoc gui");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
//                    System.out.println("Vao duoc response");
                    try {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int userId = data.getInt("id");
                        String name = (String) data.getString("name");
                        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(data.get("dob").toString());
                        String phoneNumber = (String) data.get("phoneNumber");
                        System.out.println("RESPONSE: " + name + " " + dob + " " + phoneNumber);
                        sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
                        SharedPreferences.Editor spEditor = sharedPreferences.edit();

                        spEditor.putInt("userId", userId);
                        spEditor.putBoolean("isLoggedIn", true);
                        spEditor.putString("name", name);
                        spEditor.putString("dob", new SimpleDateFormat("dd/MM/yyyy").format(dob));
                        spEditor.putString("phoneNumber", phoneNumber);
                        spEditor.apply();

                        Toast.makeText(SignInActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                        try {
                            // Lấy dữ liệu phản hồi từ server dưới dạng chuỗi JSON
                            String jsonString = new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers, "utf-8"));

                            // Chuyển đổi chuỗi JSON thành một đối tượng JSONObject
                            JSONObject jsonObject = new JSONObject(jsonString);

                            // Trích xuất thông báo lỗi từ trường "message"
                            String errorMessage = jsonObject.getString("message");

                            // Hiển thị thông báo lỗi
                            Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                        Toast.makeText(SignInActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
}