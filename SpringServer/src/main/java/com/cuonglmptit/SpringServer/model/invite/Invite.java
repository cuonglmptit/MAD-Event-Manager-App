package com.cuonglmptit.SpringServer.model.invite;

import com.cuonglmptit.SpringServer.model.event.Event;
import com.cuonglmptit.SpringServer.model.user.User;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tblInvite")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "isRead")
    private boolean isRead = false;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @Column(name = "context", columnDefinition = "TEXT")
    private String context;

    @Column(name = "confirmed")
    private InviteStatus inviteStatus;

    @Column(name = "createdDate")
    private Timestamp createdDate;

    public Invite() {
    }

    public Invite(boolean isRead, User toUser, Event event, String context, InviteStatus inviteStatus, Timestamp createdDate) {
        this.isRead = isRead;
        this.toUser = toUser;
        this.event = event;
        this.context = context;
        this.inviteStatus = inviteStatus;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Invite{" +
                "id=" + id +
                ", isRead=" + isRead +
                ", toUser=" + toUser +
                ", event=" + event +
                ", context='" + context + '\'' +
                ", inviteStatus=" + inviteStatus +
                ", createdDate=" + createdDate +
                '}';
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
