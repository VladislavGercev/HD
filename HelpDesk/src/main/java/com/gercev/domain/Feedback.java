package com.gercev.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Feedback{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private byte rate;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "user",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ticket",nullable = false)
    private Ticket ticket;

    public Feedback() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte getRate() {
        return rate;
    }

    public void setRate(byte rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
