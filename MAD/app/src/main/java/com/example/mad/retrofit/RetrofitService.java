package com.example.mad.retrofit;


import com.example.mad.model.Feedback;
import com.example.mad.model.FeedbackDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }
    public void initializeRetrofit(){

        String API_BASE_URL =  "http://10.0.2.2:8080";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .registerTypeAdapter(Feedback.class, new FeedbackDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
