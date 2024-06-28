package com.cuonglmptit.SpringServer.model.feedback;

import com.cuonglmptit.SpringServer.model.event.Event;
import com.cuonglmptit.SpringServer.model.user.User;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tblFeedback")
public class Feedback {

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "createdTime")
    private Timestamp createdTime;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    private FeedbackType feedbackType;

    private String message;

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", user=" + user +
                ", event=" + event +
                ", feedbackType=" + feedbackType +
                ", message='" + message + '\'' +
                '}';
    }

    public Feedback(User user, Event event, FeedbackType feedbackType, String message, Timestamp createdTime) {
        this.user = user;
        this.event = event;
        this.feedbackType = feedbackType;
        this.message = message;
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
