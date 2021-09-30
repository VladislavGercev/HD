package com.gercev.domain;

import javax.persistence.*;


@Entity
@Table
public class Attachment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Lob
    @Column(length = 1024*5*1024, nullable = false)
    private byte[] blob;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ticket ticket;

    public Attachment() {
    }

    public Attachment(Long id, byte[] blob, String name, Ticket ticket) {
        this.id = id;
        this.blob = blob;
        this.name = name;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
