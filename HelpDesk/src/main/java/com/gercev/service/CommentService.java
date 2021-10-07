package com.gercev.service;

import com.gercev.domain.Comment;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.exception.CommentIsNotCreatedException;
import com.gercev.exception.TicketNotFoundException;
import com.gercev.exception.UserNotFoundException;
import com.gercev.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;

    public Optional<List<Comment>> getCommentsByTicketId(long ticketId) {
        return commentRepository.getCommentsByTicketId(ticketId);
    }

    public Optional<Comment> addComment(Comment comment, long ticketId, String email) {
        try {
            User user = userService.getByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found " + email));
            Ticket ticket = ticketService.getTicketById(ticketId)
                    .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
            comment.setUser(user);
            comment.setTicket(ticket);
            comment.setDate(LocalDate.now());
            comment.setId(commentRepository.addComment(comment)
                    .orElseThrow(() -> new CommentIsNotCreatedException("Comment for ticket " + ticketId + "isn't created")));
            return Optional.of(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
