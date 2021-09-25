package com.gercev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gercev.domain.enums.State;
import com.gercev.domain.enums.Urgency;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate createdOn;

    @Column
    private LocalDate desiredResolutionDate;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private State state;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private Urgency urgency;

    @ManyToOne
    @JoinColumn
    private User assignee;

    @ManyToOne
    @JoinColumn(name="owner",nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name="approver")
    private User approver;

    @ManyToOne
    @JoinColumn(name="category")
    private Category category;

    @OneToOne
    @JoinColumn(name="feedback")
    private Feedback feedback;

    @Transient
    private List<Comment> comments;

    @Transient
    private List<History> histories;

    @Transient
    private List<Attachment> attachments;

    public Ticket() {
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

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


}
