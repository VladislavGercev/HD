package com.gercev.dto;

import java.time.LocalDate;

public class FeedbackDto {
    private Long id;
    private byte rate;
    private LocalDate date;
    private String text;
    private TicketDto ticketDTO;
    private UserDto userDTO;

    public FeedbackDto() {
    }

    public FeedbackDto(Long id, byte rate, LocalDate date, String text) {
        this.id = id;
        this.rate = rate;
        this.date = date;
        this.text = text;
    }

    public UserDto getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDto userDTO) {
        this.userDTO = userDTO;
    }

    public TicketDto getTicketDTO() {
        return ticketDTO;
    }

    public void setTicketDTO(TicketDto ticketDTO) {
        this.ticketDTO = ticketDTO;
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
}
