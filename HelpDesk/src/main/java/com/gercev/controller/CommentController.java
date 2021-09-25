package com.gercev.controller;

import com.gercev.converter.CommentConverter;
import com.gercev.domain.Comment;
import com.gercev.domain.User;
import com.gercev.dto.CommentDto;
import com.gercev.service.CommentService;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    public UserService userService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public CommentConverter commentConverter;

    @GetMapping("/tickets/{id}/comments")
    public ResponseEntity getHistoriesById(Principal principal, @PathVariable String id) {
        List<Comment> comments = commentService.getCommentsByTicketId(Long.parseLong(id));
        List<CommentDto> commentDtos = comments.stream().map(commentConverter::convert).collect(Collectors.toList());

        return ResponseEntity.ok(commentDtos);
    }

    @PostMapping("/tickets/{id}/comments")
    public ResponseEntity addComment(Principal principal, @RequestBody CommentDto commentDTO) {
        return ResponseEntity.ok(commentConverter.convert(
                commentService.addComment(commentConverter.convert(commentDTO), principal.getName())));
    }
}
