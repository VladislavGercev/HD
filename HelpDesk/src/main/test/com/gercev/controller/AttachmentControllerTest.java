package com.gercev.controller;

import com.gercev.domain.Attachment;
import com.gercev.domain.History;
import com.gercev.domain.Ticket;
import com.gercev.repository.AttachmentRepository;
import com.gercev.service.AttachmentService;
import com.gercev.service.HistoryService;
import com.gercev.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class AttachmentControllerTest {

    private AttachmentService attachmentService;
    @Mock
    private HistoryService historyService;
    @Mock
    private TicketService ticketService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.attachmentService = new AttachmentService(attachmentRepository, historyService, ticketService);
    }

    @Test
    void testAddAttachment() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        given(ticketService.getTicketById(1L)).willReturn(Optional.of(ticket));
        given(historyService.addAttachment(new Attachment())).willReturn(Optional.of(new History()));
        given(attachmentRepository.addAttachment(new Attachment())).willReturn(Optional.of(1L));

        Attachment attachment = new Attachment(1L, new byte[]{1, 2}, "name", new Ticket());
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        Optional<List<Long>> optionalIdList = attachmentService.addAttachment(new CommonsMultipartFile[]{}, 1L);
        assertEquals(1L,optionalIdList.orElse(null));
    }

    @Test
    void testGetAttachmentsByIdWithValue() {
        Attachment attachment = new Attachment(1L, new byte[]{1, 2}, "name", new Ticket());
        given(attachmentRepository.getAttachmentById(1L)).willReturn(Optional.of(attachment));
        Long id = Objects.requireNonNull(attachmentService.getAttachmentById(1L).orElse(null)).getId();
        Assertions.assertEquals(new Long(1), id);
    }

    @Test
    void testGetAttachmentsByIdWithoutValue() {
        given(attachmentRepository.getAttachmentById(1L)).willReturn(Optional.empty());
        Optional<Attachment> attachmentOptional = attachmentService.getAttachmentById(1L);
        Assertions.assertNull(attachmentOptional.orElse(null));
    }

    @Test
    void testGetAttachmentsByTicketIdWithValue() {
        Attachment attachment = new Attachment(1L, new byte[]{1, 2}, "name", new Ticket());
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        given(attachmentRepository.getAttachmentsByTicketId(1L)).willReturn(Optional.of(attachments));
        Long id = Objects.requireNonNull(attachmentService.getAttachmentsByTicketId(1L).orElse(null)).get(0).getId();
        Assertions.assertEquals(new Long(1), id);
    }

    @Test
    void testGetAttachmentsByTicketIdWithoutValue() {
        given(attachmentRepository.getAttachmentsByTicketId(1L)).willReturn(Optional.empty());
        Optional<List<Attachment>> attachmentList = attachmentService.getAttachmentsByTicketId(1L);
        Assertions.assertNull(attachmentList.orElse(null));
    }

    @Test
    void testDeleteAttachmentsTrue() {
        given(attachmentRepository.deleteAttachment(1L)).willReturn(true);
        assertTrue(attachmentService.deleteAttachment(1L));
    }

    @Test
    void testDeleteAttachmentsFoals() {
        given(attachmentRepository.deleteAttachment(1L)).willReturn(false);
        assertFalse(attachmentService.deleteAttachment(1L));
    }
}