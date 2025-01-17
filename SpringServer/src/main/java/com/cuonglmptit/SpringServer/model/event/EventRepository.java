package com.cuonglmptit.SpringServer.model.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository <Event, Integer>{
    List<Event> findByUserId(int userId);
}
