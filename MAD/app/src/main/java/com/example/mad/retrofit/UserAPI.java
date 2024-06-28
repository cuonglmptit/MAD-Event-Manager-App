package com.example.mad.retrofit;

import com.example.mad.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPI {
    @GET("/user/{idusername}")
    Call<User> getUser(@Path("idusername") String eventId);
}
