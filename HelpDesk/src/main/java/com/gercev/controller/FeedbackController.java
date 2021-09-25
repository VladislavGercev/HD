package com.gercev.controller;

import com.gercev.converter.FeedbackConverter;
import com.gercev.domain.Feedback;
import com.gercev.dto.FeedbackDto;
import com.gercev.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
public class FeedbackController {

    @Autowired
    public FeedbackService feedbackService;
    @Autowired
    public FeedbackConverter feedbackConverter;

    @GetMapping("/tickets/{id}/feedback")
    public ResponseEntity getFeedback(@PathVariable long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackByTicketId(id);
        if(feedback.isPresent()){
            return ResponseEntity.ok(feedbackConverter.convert(feedback.get()));
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/tickets/{id}/feedback")
    public ResponseEntity addFeedback(@PathVariable long id, @RequestBody FeedbackDto feedbackDTO, Principal principal) {
        return ResponseEntity.ok(
                feedbackService
                        .addFeedback(feedbackConverter.convert(feedbackDTO), principal.getName(), id));
    }
}
