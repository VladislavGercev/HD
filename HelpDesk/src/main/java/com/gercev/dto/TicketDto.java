package com.gercev.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gercev.domain.enums.State;
import com.gercev.domain.enums.Urgency;

import java.time.LocalDate;

public class TicketDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate createdOn;
    private LocalDate desiredResolutionDate;
    private State state;
    private Urgency urgency;
    private UserDto assignee;
    private UserDto owner;
    private UserDto approver;
    private CategoryDto category;
    private CommentDto comment;

    public TicketDto() {
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }

    public UserDto getApprover() {
        return approver;
    }

    public void setApprover(UserDto approver) {
        this.approver = approver;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public void setDesiredResolutionDate(LocalDate desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDto assignee) {
        this.assignee = assignee;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
