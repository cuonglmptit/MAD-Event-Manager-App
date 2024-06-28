package com.cuonglmptit.SpringServer.model.invite;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface InviteRepository extends CrudRepository<Invite, Integer> {
    List<Invite> findByEventId(int eventId);

    @Query("SELECT i FROM Invite i WHERE i.event.id = :eventId ORDER BY i.id DESC")
    List<Invite> findByEventIdOrderByCreatedTimeDesc(@Param("eventId") int eventId);

    @Query("SELECT i FROM Invite i WHERE i.toUser.id = :userId AND i.event.id = :eventId")
    Invite findInviteByUserIdAndEventId(@Param("userId") int userId, @Param("eventId") int eventId);

    @Query("SELECT i FROM Invite i WHERE i.event.id = :eventId AND i.inviteStatus = com.cuonglmptit.SpringServer.model.invite.InviteStatus.ACCEPTED")
    ArrayList<Invite> findAcceptedInvitesByEventId(@Param("eventId") int eventId);

    @Query("SELECT COUNT(i) FROM Invite i WHERE i.event.id = :eventId AND i.inviteStatus = com.cuonglmptit.SpringServer.model.invite.InviteStatus.ACCEPTED")
    int countAcceptedInvitesByEventId(@Param("eventId") int eventId);

    @Query("SELECT i FROM Invite i WHERE i.toUser.id = :userId")
    ArrayList<Invite> findByUserId(@Param("userId") int userId);

    @Query("SELECT i FROM Invite i WHERE i.toUser.id = :userId AND i.isRead = false")
    ArrayList<Invite> findUnreadInvitesByUserId(@Param("userId") int userId);
}
