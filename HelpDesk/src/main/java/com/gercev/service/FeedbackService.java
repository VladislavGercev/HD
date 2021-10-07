package com.gercev.service;

import com.gercev.domain.Feedback;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import com.gercev.exception.FeedbackIsNotCreatedException;
import com.gercev.exception.TicketNotFoundException;
import com.gercev.exception.UserNotFoundException;
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

    public Optional<Feedback> addFeedback(Feedback feedback, String email, long ticketId) {
        try {
            User user = userService.getByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found " + email));
            Ticket ticket = ticketService.getTicketById(ticketId)
                    .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
            if (feedbackValidate(ticket, user)) {
                feedback.setUser(user);
                feedback.setTicket(ticket);
                feedback.setDate(LocalDate.now());
                feedback.setId(feedbackRepository.addFeedback(feedback)
                        .orElseThrow(() -> new FeedbackIsNotCreatedException("Feedback for ticket " + ticketId + " isn't created")));
                return Optional.of(feedback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean feedbackValidate(Ticket ticket, User user) {
        return ticket.getState() == State.DONE && ticket.getOwner().equals(user);
    }
}
