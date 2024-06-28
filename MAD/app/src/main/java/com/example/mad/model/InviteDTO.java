package com.example.mad.model;

import java.util.HashMap;

public class InviteDTO {
    private int toUser, eventId;
    private InviteStatus inviteStatus;
    private String context;

    public InviteDTO() {
    }

    public InviteDTO(int toUser, int eventId, InviteStatus inviteStatus, String context) {
        this.toUser = toUser;
        this.eventId = eventId;
        this.inviteStatus = inviteStatus;
        this.context = context;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> params = new HashMap<>();
        params.put("toUser", String.valueOf(toUser));
        params.put("eventId", String.valueOf(eventId));
        params.put("inviteStatus", inviteStatus.toString());
        params.put("context", context);
        return params;
    }
}
