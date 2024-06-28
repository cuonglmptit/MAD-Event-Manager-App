package com.example.mad.retrofit;

import com.example.mad.model.Event;
import com.example.mad.model.Feedback;
import com.example.mad.model.Invite;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInviteAPI {
    @GET("/invite/get-invited-user-of-event/{eventId}")
    Call<ArrayList<Invite>> getAllInvitedUserOfEvent(@Path("eventId") int eventId);

    @GET("/invite/get-invites-of-user/{userId}")
    Call<ArrayList<Invite>> getAllInviteOfUser(@Path("userId") int userId);
}
