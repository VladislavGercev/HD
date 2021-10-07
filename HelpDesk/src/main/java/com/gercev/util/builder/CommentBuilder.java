package com.gercev.util.builder;

import com.gercev.domain.Comment;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;

import java.time.LocalDate;

public class CommentBuilder {

    private Long id;

    private LocalDate date;

    private String text;

    private Ticket ticket;

    private User user;


    public CommentBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CommentBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public CommentBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public CommentBuilder setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }


    public CommentBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setDate(date);
        comment.setText(text);
        comment.setTicket(ticket);
        comment.setUser(user);
        return comment;
    }
}
