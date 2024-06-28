package com.cuonglmptit.SpringServer.model.invite;

import com.cuonglmptit.SpringServer.model.feedback.FeedbackType;

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
}
