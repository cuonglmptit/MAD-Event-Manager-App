package com.cuonglmptit.SpringServer.controller;

import com.cuonglmptit.SpringServer.model.ResponseData;
import com.cuonglmptit.SpringServer.model.event.EventDAO;
import com.cuonglmptit.SpringServer.model.feedback.Feedback;
import com.cuonglmptit.SpringServer.model.feedback.FeedbackDAO;
import com.cuonglmptit.SpringServer.model.feedback.FeedbackDTO;
import com.cuonglmptit.SpringServer.model.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @GetMapping("/get-feedback/{eventId}")
    public ArrayList<Feedback> getAllFeedbackOfAnEvent(@PathVariable("eventId") int eventId) {
        return feedbackDAO.getAllFeedbackByEventID(eventId);
    }

    @PostMapping("/post")
    public ResponseEntity postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            System.out.println("Post: " + feedbackDTO);
            // Tạo đối tượng Date hiện tại
            Date currentDate = new Date();
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(currentDate.getTime());

            Feedback feedback = new Feedback(userDAO.getUserByUserId(feedbackDTO.getUserId()),
                    eventDAO.getEventByID(feedbackDTO.getEventId()),
                    feedbackDTO.getType(), feedbackDTO.getMessage(), sqlDate);
            feedbackDAO.save(feedback);
            System.out.println(feedback);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Bình luận thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Lỗi gửi bình luận"));
        }
    }
}
