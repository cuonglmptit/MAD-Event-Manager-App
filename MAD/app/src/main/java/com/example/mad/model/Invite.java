package com.example.mad.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Invite implements Serializable {
    private int id;

    private User toUser;

    private boolean read;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Invite{" +
                "id=" + id +
                ", toUser=" + toUser +
                ", isRead=" + read +
                ", event=" + event +
                ", context='" + context + '\'' +
                ", inviteStatus=" + inviteStatus +
                ", createdDate=" + createdDate +
                '}';
    }

    private Event event;

    private String context;

    private InviteStatus inviteStatus;

    private Timestamp createdDate;

    public Invite() {
    }

    public Invite(boolean read, User toUser, Event event, String context, InviteStatus inviteStatus, Timestamp createdDate) {
        this.read = read;
        this.toUser = toUser;
        this.event = event;
        this.context = context;
        this.inviteStatus = inviteStatus;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public User getToUser() {
        return toUser;
    }

    public Event getEvent() {
        return event;
    }

    public String getContext() {
        return context;
    }

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
}
