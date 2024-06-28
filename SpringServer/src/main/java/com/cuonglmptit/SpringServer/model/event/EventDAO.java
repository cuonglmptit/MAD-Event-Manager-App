package com.cuonglmptit.SpringServer.model.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventDAO {
    @Autowired
    private EventRepository repository;

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        for (Event event : repository.findAll()) {
            events.add(event);
        }
        return events;
    }

    public Event getEventByID(int eventId){
        Event event = repository.findById(eventId).get();
        return event;
    }

    public List<Event> getAllEventByUserID(int userID){
        List<Event> events = new ArrayList<>();
        for (Event event : repository.findByUserId(userID)) {
            events.add(event);
        }
        return events;
    }
    public void save(Event event) {
        repository.save(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }
}
