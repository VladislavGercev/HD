package com.gercev.util.builder;

import com.gercev.domain.History;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;

import java.time.LocalDate;

public class HistoryBuilder {

    private Long id;

    private LocalDate date;

    private String action;

    private String description;

    private Ticket ticket;

    private User user;

    public HistoryBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public HistoryBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public HistoryBuilder setAction(String action) {
        this.action = action;
        return this;
    }

    public HistoryBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public HistoryBuilder setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public HistoryBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public History build(){
        History history = new History();
        history.setId(this.id);
        history.setDate(this.date);
        history.setAction(this.action);
        history.setDescription(this.description);
        history.setTicket(this.ticket);
        history.setUser(this.user);
        return history;
    }
}
