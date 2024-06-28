package com.example.mad.retrofit;

import com.example.mad.model.Feedback;
import com.example.mad.model.FeedbackDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackAPI {
    @GET("/feedback/get-feedback/{eventId}")
    Call<ArrayList<Feedback>> getAllFeedbackOfEvent(@Path("eventId") int eventId);

    @POST("/feedback/post")
    Call<Feedback> save(@Body FeedbackDTO feedbackDTO);
}
