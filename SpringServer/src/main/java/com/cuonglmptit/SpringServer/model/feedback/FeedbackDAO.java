package com.cuonglmptit.SpringServer.model.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FeedbackDAO {
    @Autowired
    private FeedbackRepositopry repositopry;

    public ArrayList<Feedback> getAllFeedbackByEventID(int eventID){
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        feedbacks.addAll(repositopry.findByEventIdOrderByCreatedTimeDesc(eventID));
        return feedbacks;
    }

    public void save(Feedback feedback){
        repositopry.save(feedback);
    }
}
