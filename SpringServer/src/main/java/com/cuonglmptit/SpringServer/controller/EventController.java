package com.cuonglmptit.SpringServer.controller;

import com.cuonglmptit.SpringServer.model.ResponseData;
import com.cuonglmptit.SpringServer.model.event.Event;
import com.cuonglmptit.SpringServer.model.event.EventDAO;
import com.cuonglmptit.SpringServer.model.event.EventDAO;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventDAO eventDAO;

    @GetMapping("/event/get-all")
    public ResponseEntity getAllEvents() throws SQLException {
        List<Event> events = eventDAO.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).
                body(events);
    }

    @GetMapping("/event/get-one/{id}")
    public Event getAnEvent(@PathVariable("id") int id) throws SQLException {
        System.out.println(id);
        return eventDAO.getEventByID(id);
    }

    @GetMapping("/event/get-by-user/{id}")
    public List<Event> getAllEventByUserId(@PathVariable("id") int id) throws SQLException {
//        System.out.println(id);
        return eventDAO.getAllEventByUserID(id);
    }

}
