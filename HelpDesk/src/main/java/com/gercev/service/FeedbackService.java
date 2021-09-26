package com.gercev.service;

import com.gercev.domain.Feedback;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    public FeedbackRepository feedbackRepository;
    @Autowired
    public UserService userService;
    @Autowired
    public TicketService ticketService;

    public Optional<Feedback> getFeedbackByTicketId(long feedbackId) {
        return feedbackRepository.getFeedbackByTicketId(feedbackId);
    }

    public Optional<Long> addFeedback(Feedback feedback, String email, long ticketId) {
        Optional<User> userOptional = userService.getByEmail(email);
        Optional<Ticket> ticketOptional = ticketService.getTicketById(ticketId);
        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            feedback.setUser(userOptional.get());
            feedback.setTicket(ticketOptional.get());
            feedback.setDate(LocalDate.now());
            Optional<Long> feedbackIdOptional = feedbackRepository.addFeedback(feedback);
            if (feedbackIdOptional.isPresent()) {
                feedback.setId(feedbackIdOptional.get());
                return feedbackIdOptional;
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
