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
    public ResponseEntity<?> getFeedback(@PathVariable("id") long ticketId) {
        Optional<Feedback> feedbackOptional = feedbackService.getFeedbackByTicketId(ticketId);
        return feedbackOptional
                .map(value -> ResponseEntity.ok(feedbackConverter.convert(value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/tickets/{id}/feedback")
    public ResponseEntity<?> addFeedback(@PathVariable("id") long ticketId, @RequestBody FeedbackDto feedbackDTO, Principal principal) {
        Optional<Long> feedbackIdOptional = feedbackService
                .addFeedback(feedbackConverter.convert(feedbackDTO), principal.getName(), ticketId);
        return feedbackIdOptional.isPresent()
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
