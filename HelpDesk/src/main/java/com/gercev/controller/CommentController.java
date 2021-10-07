package com.gercev.controller;

import com.gercev.converter.CommentConverter;
import com.gercev.domain.Comment;
import com.gercev.dto.CommentDto;
import com.gercev.service.CommentService;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {


    public UserService userService;


    public CommentService commentService;


    public CommentConverter commentConverter;

    @Autowired
    public CommentController(UserService userService, CommentService commentService, CommentConverter commentConverter) {
        this.userService = userService;
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @GetMapping("/tickets/{id}/comments")
    public ResponseEntity<?> getCommentsByTicketId(@PathVariable("id") Long ticketId) {
        Optional<List<Comment>> commentsOptional = commentService.getCommentsByTicketId(ticketId);
        return commentsOptional.map(comments -> ResponseEntity.ok(comments
                .stream()
                .map(commentConverter::convert)
                .toArray()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tickets/{id}/comments")
    public ResponseEntity<?> addCommentForTicket(Principal principal, @RequestBody CommentDto commentDTO, @PathVariable("id") Long ticketId) {
        Optional<Comment> commentOptional = commentService.addComment(commentConverter.convert(commentDTO), ticketId, principal.getName());
        return commentOptional.isPresent()
                ? new ResponseEntity<>(commentConverter.convert(commentOptional.get()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
