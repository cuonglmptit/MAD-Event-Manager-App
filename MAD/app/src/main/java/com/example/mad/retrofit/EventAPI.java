package com.example.mad.retrofit;

import com.example.mad.model.Event;
import com.example.mad.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventAPI {
    @GET("/event/get-all")
    Call<ArrayList<Event>> getAllEvents();
    @GET("/event/get-by-user/{userId}")
    Call<ArrayList<Event>> getAllEventsByUserID(@Path("userId") int id);
}
