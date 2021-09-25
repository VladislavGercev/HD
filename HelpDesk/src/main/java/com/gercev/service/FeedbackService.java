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

    public Feedback addFeedback(Feedback feedback, String email, long ticketId) {
        Optional<User> userOptional = userService.getByEmail(email);
        User user = userOptional.get();
        Ticket ticket = ticketService.getTicketById(ticketId);
        feedback.setUser(user);
        feedback.setTicket(ticket);
        feedback.setDate(LocalDate.now());
        feedback.setId(feedbackRepository.addFeedback(feedback));
        return feedback;
    }
}
