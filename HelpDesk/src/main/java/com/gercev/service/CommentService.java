package com.gercev.service;

import com.gercev.domain.Comment;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
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
        Optional<User> userOptional = userService.getByEmail(email);
        Optional<Ticket> ticketOptional = ticketService.getTicketById(ticketId);
        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            comment.setUser(userOptional.get());
            comment.setTicket(ticketOptional.get());
            comment.setDate(LocalDate.now());
            Optional<Long> commentIdOptional = commentRepository.addComment(comment);
            if (commentIdOptional.isPresent()) {
                comment.setId(commentIdOptional.get());
                return Optional.of(comment);
            }
        }
        return Optional.empty();
    }
}
