package com.gercev.service;

import com.gercev.domain.Comment;
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

    public List<Comment> getCommentsByTicketId(long ticketId){
        return commentRepository.getCommentsByTicketId(ticketId);
    }

    public Comment addComment(Comment comment, String email){
        Optional<User> userOptional = userService.getByEmail(email);
        User user = userOptional.get();
        comment.setUser(user);
        comment.setDate(LocalDate.now());
        comment.setId(commentRepository.addComment(comment));
        return comment;
    }
}
