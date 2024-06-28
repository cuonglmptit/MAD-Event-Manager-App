package com.example.mad;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mad.adapter.FeedbackRVAdapter;
import com.example.mad.adapter.FeedbackTypeAdapter;
import com.example.mad.model.Event;
import com.example.mad.model.Feedback;
import com.example.mad.model.FeedbackDTO;
import com.example.mad.model.FeedbackType;
import com.example.mad.retrofit.FeedbackAPI;
import com.example.mad.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFeedbackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView feedbackRecyclerView;
    private TextView txtViewOverall, txtViewPositive, txtViewNeutral, txtViewNegative;
    private ImageView imgViewOverall;
    private Spinner spnFeedbackType;
    private FloatingActionButton fltBtnAddFeedback;

    private SharedPreferences sharedUserStatusPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed_back);
        initInstances();
        fetchFeedbackData();
    }

    private void setupFloatingActionBtn(Event event) {
        fltBtnAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogAddFeedback = new Dialog(v.getContext());
                dialogAddFeedback.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAddFeedback.setContentView(R.layout.dialog_add_feedback);
                Window window = dialogAddFeedback.getWindow();
                if (window == null) return;

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);

                TextView txtViewMoreOptions = dialogAddFeedback.findViewById(R.id.txtViewAddFeedback);
                txtViewMoreOptions.setText(txtViewMoreOptions.getText() + " đến\n" + event.getName());
                dialogAddFeedback.setCancelable(true);
                setupAddFeedbackButtons(dialogAddFeedback, event);
                dialogAddFeedback.show();
            }
        });
    }

    private void setupAddFeedbackButtons(Dialog dialogAddFeedback, Event event) {
        Button btnCancelAddFeedback = dialogAddFeedback.findViewById(R.id.btnCancelAddFeedback);
        Button btnSendAddFeedback = dialogAddFeedback.findViewById(R.id.btnSendAddFeedback);

        btnCancelAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddFeedback.dismiss();
                Toast.makeText(MainFeedbackActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });


        RetrofitService retrofitService = new RetrofitService();
        FeedbackAPI feedbackAPI = retrofitService.getRetrofit().create(FeedbackAPI.class);
        btnSendAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtTextAddFeedback = dialogAddFeedback.findViewById(R.id.edtTextAddFeedback);
                RadioButton radBtnPositive = dialogAddFeedback.findViewById(R.id.radBtnPositive);
                RadioButton radBtnNeutral = dialogAddFeedback.findViewById(R.id.radBtnNeutral);
                RadioButton radBtnNegative = dialogAddFeedback.findViewById(R.id.radBtnNegative);

                String message = edtTextAddFeedback.getText().toString();
                FeedbackDTO feedback = new FeedbackDTO();
                if (message != null && !message.isEmpty()) {
                    feedback.setMessage(message);
                } else {
                    Toast.makeText(MainFeedbackActivity.this, "Vui lòng nhập suy nghĩ của bạn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radBtnPositive.isChecked()) {
                    feedback.setType("POSITIVE");
                } else if (radBtnNeutral.isChecked()) {
                    feedback.setType("NEUTRAL");

                } else if (radBtnNegative.isChecked()) {
                    feedback.setType("NEGATIVE");
                } else {
                    Toast.makeText(MainFeedbackActivity.this, "Chưa chọn đánh giá!", Toast.LENGTH_SHORT).show();
                    return;
                }

                feedback.setUserId(sharedUserStatusPreferences.getInt("userId", 0));
                feedback.setEventId(event.getId());
                RequestQueue requestQueue = Volley.newRequestQueue(MainFeedbackActivity.this);
                String url = "http://10.0.2.2:8080/feedback/post";

                HashMap<String, String> params = feedback.getHashMapParams();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String message = (String) jsonObject.getString("message");
                            dialogAddFeedback.dismiss();
                            fetchFeedbackData();
                            Toast.makeText(MainFeedbackActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                            try {
                                String jsonString = new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers, "utf-8"));

                                JSONObject jsonObject = new JSONObject(jsonString);

                                String errorMessage = jsonObject.getString("message");

                                Toast.makeText(MainFeedbackActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            // Xử lý khi không có phản hồi từ server hoặc có lỗi không xác định
                            Toast.makeText(MainFeedbackActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                requestQueue.add(jsonObjectRequest);
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
    }

    private void setupSpinner(ArrayList<Feedback> feedbacks) {
        spnFeedbackType = findViewById(R.id.spnFeedbackType);
        List<FeedbackType> feedbackTypes = new ArrayList<>();
        for (FeedbackType type : FeedbackType.values()) {
            feedbackTypes.add(type);
        }
        FeedbackTypeAdapter feedbackTypeAdapter = new FeedbackTypeAdapter(this, R.layout.item_type_feedback_selected, feedbackTypes);
        spnFeedbackType.setAdapter(feedbackTypeAdapter);
        spnFeedbackType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FeedbackType selectedType = (FeedbackType) parent.getItemAtPosition(position);
                ArrayList<Feedback> filteredFeedbacks = filterFeedbacksByType(feedbacks, selectedType);
                setupFeedbackRecyclerView(filteredFeedbacks);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<Feedback> filterFeedbacksByType(ArrayList<Feedback> feedbacks, FeedbackType selectedType) {
        if (selectedType == FeedbackType.ALL) return feedbacks;
        ArrayList<Feedback> filteredFeedbacks = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getType() == selectedType) {
                filteredFeedbacks.add(feedback);
            }
        }
        return filteredFeedbacks;
    }

    private void fetchFeedbackData() {
        RetrofitService retrofitService = new RetrofitService();
        FeedbackAPI feedbackAPI = retrofitService.getRetrofit().create(FeedbackAPI.class);
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        System.out.println("Intet event gia tri: " + event);
        feedbackAPI.getAllFeedbackOfEvent(event.getId()).enqueue(new Callback<ArrayList<Feedback>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedback>> call, Response<ArrayList<Feedback>> response) {
                ArrayList<Feedback> feedbacks = response.body();
                for (Feedback feedback : feedbacks) {
                    System.out.println(feedback);
                }
                setupFeedbackRecyclerView(feedbacks);
                setupOverall(feedbacks);
                setupEventTitle(event);
                setupSpinner(feedbacks);
                setupFloatingActionBtn(event);
            }

            @Override
            public void onFailure(Call<ArrayList<Feedback>> call, Throwable t) {
                System.out.println("Loi get du lieu");
                Toast.makeText(MainFeedbackActivity.this, "loi get du lieu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupOverall(ArrayList<Feedback> feedbacks) {
        int totalFeedbacks = feedbacks.size();
        int positiveCount = 0;
        int neutralCount = 0;
        int negativeCount = 0;

        // Đếm số lượng đánh giá của từng loại
        for (Feedback feedback : feedbacks) {
            if (feedback.getType() == FeedbackType.POSITIVE) {
                positiveCount++;
            } else if (feedback.getType() == FeedbackType.NEUTRAL) {
                neutralCount++;
            } else if (feedback.getType() == FeedbackType.NEGATIVE) {
                negativeCount++;
            }
        }

        // Tính phần trăm của từng loại đánh giá
        float positivePercentage = (float) positiveCount / totalFeedbacks;
        float neutralPercentage = (float) neutralCount / totalFeedbacks;
        float negativePercentage = (float) negativeCount / totalFeedbacks;

        // So sánh giá trị của các biến để tìm biến có giá trị lớn nhất
        float maxPercentage = Math.max(positivePercentage, Math.max(neutralPercentage, negativePercentage));
        if (negativeCount == 0 && positiveCount == 0 && neutralCount == 0) {
            txtViewOverall.setText("Chưa có Feedback nào!");
            imgViewOverall.setImageResource(0);
        } else {
            if (maxPercentage == positivePercentage) {
                txtViewOverall.setText("Tích cực");
                imgViewOverall.setImageResource(FeedbackType.POSITIVE.getImage());
            } else if (maxPercentage == neutralPercentage) {
                txtViewOverall.setText("Bình thường");
                imgViewOverall.setImageResource(FeedbackType.NEUTRAL.getImage());
            } else {
                txtViewOverall.setText("Tiêu cực");
                imgViewOverall.setImageResource(FeedbackType.NEGATIVE.getImage());
            }
        }

        txtViewNegative.setText("Tiêu cực: " + negativeCount);
        txtViewPositive.setText("Tích cực: " + positiveCount);
        txtViewNeutral.setText("Bình thường: " + neutralCount);
    }

    private void setupFeedbackRecyclerView(ArrayList<Feedback> feedbacks) {
        FeedbackRVAdapter feedbackRVAdapter = new FeedbackRVAdapter(this, feedbacks);
        feedbackRecyclerView.setAdapter(feedbackRVAdapter);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initInstances() {
        toolbar = findViewById(R.id.toolBarMainFeedBackActivity);
        setSupportActionBar(toolbar);
        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        feedbackRecyclerView = findViewById(R.id.mainFeedBackRecyclerView);
        txtViewOverall = findViewById(R.id.txtViewOverall);
        txtViewPositive = findViewById(R.id.txtViewPositive);
        txtViewNegative = findViewById(R.id.txtViewNegative);
        txtViewNeutral = findViewById(R.id.txtViewNeutral);
        imgViewOverall = findViewById(R.id.imgViewOverall);

        fltBtnAddFeedback = findViewById(R.id.fltBtnAddFeedback);
        sharedUserStatusPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
    }

}