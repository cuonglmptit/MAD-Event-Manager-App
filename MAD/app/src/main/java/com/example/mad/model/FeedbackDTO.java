package com.example.mad.model;

import java.util.HashMap;

public class FeedbackDTO {
    private int userId;
    private int eventId;
    private String type, message;

    public HashMap<String, String> getHashMapParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("eventId", eventId + "");
        params.put("type", type);
        params.put("message", message);


        return params;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "userId=" + userId +
                ", eventId=" + eventId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public FeedbackDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeedbackDTO(int userId, int eventId, String type, String message) {
        this.userId = userId;
        this.eventId = eventId;
        this.type = type;
        this.message = message;
    }
}
