package com.gercev.util.builder;

import com.gercev.domain.Attachment;
import com.gercev.domain.Ticket;

public class AttachmentBuilder {

    private Long id;

    private byte[] blob;

    private String name;

    private Ticket ticket;

    public AttachmentBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public AttachmentBuilder setBlob(byte[] blob) {
        this.blob = blob;
        return this;
    }

    public AttachmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AttachmentBuilder setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public Attachment builder() {
        Attachment attachment = new Attachment();
        attachment.setId(this.id);
        attachment.setBlob(this.blob);
        attachment.setName(this.name);
        attachment.setTicket(this.ticket);
        return attachment;
    }
}
