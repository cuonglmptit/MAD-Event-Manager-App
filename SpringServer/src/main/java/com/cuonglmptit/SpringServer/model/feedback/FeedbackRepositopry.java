package com.cuonglmptit.SpringServer.model.feedback;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepositopry extends CrudRepository<Feedback, Integer> {
    List<Feedback> findByEventId(int eventId);

    @Query("SELECT f FROM Feedback f WHERE f.event.id = :eventId ORDER BY f.id DESC")
    List<Feedback> findByEventIdOrderByCreatedTimeDesc(@Param("eventId") int eventId);
}
