package com.cuonglmptit.SpringServer.model.feedback;


public class FeedbackDTO {
    private int userId, eventId;
    private FeedbackType feedbackType;
    private String message;

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "userId=" + userId +
                ", eventId=" + eventId +
                ", type=" + feedbackType +
                ", message='" + message + '\'' +
                '}';
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

    public FeedbackType getType() {
        return feedbackType;
    }

    public void setType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
