package com.cuonglmptit.SpringServer.model.invite;

import com.cuonglmptit.SpringServer.model.feedback.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InviteDAO {
    @Autowired
    InviteRepository repository;

    public void save(Invite invite) {
        repository.save(invite);
    }

    public ArrayList<Invite> getInvitesByEventId(int eventId) {
        ArrayList<Invite> invites = new ArrayList<>();
        for (Invite invite : repository.findByEventIdOrderByCreatedTimeDesc(eventId)) {
            invites.add(invite);
        }
        return invites;
    }

    public Invite getInviteByUserIdAndEventId(int userId, int eventId) {
        return repository.findInviteByUserIdAndEventId(userId, eventId);
    }

    public int getCountAcceptedInvitesByEventId(int eventId) {
        return repository.countAcceptedInvitesByEventId(eventId);
    }

    public ArrayList<Invite> findAcceptedInvitesByEventId(int eventId) {
        return repository.findAcceptedInvitesByEventId(eventId);
    }

    public Invite findById(int inviteId) {
        return repository.findById(inviteId).get();
    }

    public ArrayList<Invite> findInvitesByUserId(int userId){
        return repository.findByUserId(userId);
    }

    public ArrayList<Invite> findUnreadInvitesByUserId(int userId) {
        return repository.findUnreadInvitesByUserId(userId);
    }
}
