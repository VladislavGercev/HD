package com.gercev.controller;

import com.gercev.converter.CommentConverter;
import com.gercev.domain.Comment;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.dto.CommentDto;
import com.gercev.service.CommentService;
import com.gercev.service.UserService;
import com.gercev.util.builder.CommentBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

class CommentControllerTest {

    @Mock
    private CommentService commentService;
    @Mock
    private CommentController commentController;
    @Mock
    private UserService userService;
    @InjectMocks
    private CommentConverter commentConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.commentService = new CommentService();
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService();
        MockitoAnnotations.initMocks(this);
        this.commentConverter = new CommentConverter(new ModelMapper());
        this.commentController = new CommentController(userService, commentService, commentConverter);
    }

    @Test
    void testGetCommentsByTicketIdWithValue() {
        List<Comment> commentList = new ArrayList<>();
        Comment comment = new CommentBuilder()
                .setId(1L)
                .setDate(LocalDate.now())
                .setText("someText")
                .setTicket(new Ticket())
                .setUser(new User()).build();
        commentList.add(comment);
        given(this.commentService.getCommentsByTicketId(1L)).willReturn(Optional.of(commentList));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(comment, CommentDto.class);
        Object[] commentDtoArray = commentList
                .stream()
                .map((e) -> modelMapper.map(e, CommentDto.class))
                .toArray();
        ResponseEntity<?> responseEntity = commentController.getCommentsByTicketId(1L);
        Assertions.assertArrayEquals(commentDtoArray, (Object[]) responseEntity.getBody());
    }

    @Test
    void testGetCommentsByTicketIdWithoutValue() {
        given(this.commentService.getCommentsByTicketId(1L)).willReturn(Optional.empty());
        ResponseEntity<?> responseEntity = commentController.getCommentsByTicketId(1L);
        Assertions.assertArrayEquals(null, (Object[]) responseEntity.getBody());
    }

    @Test
    void testGetCommentsByTicketIdWithNotFound() {
        given(this.commentService.getCommentsByTicketId(1L)).willReturn(Optional.empty());
        ResponseEntity<?> responseEntity = commentController.getCommentsByTicketId(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addCommentForTicket() {
//        Comment comment = new CommentBuilder()
//                .setId(1L)
//                .setDate(LocalDate.now())
//                .setText("someText")
//                .setTicket(new Ticket())
//                .setUser(new User()).build();
//        ModelMapper modelMapper = new ModelMapper();
//        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        given(commentService.addComment(comment, 1L, "email"))
//                .willReturn(Optional.of(comment));
//        ResponseEntity<?> responseEntity = commentController.addCommentForTicket(() -> "email", commentDto, 1L);
//        assertEquals(commentDto, responseEntity.getBody());
    }
}
