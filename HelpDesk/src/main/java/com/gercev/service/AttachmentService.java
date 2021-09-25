package com.gercev.service;

import com.gercev.domain.Attachment;
import com.gercev.domain.Ticket;
import com.gercev.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Service
@Transactional
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TicketService ticketService;

    public void addAttachment(CommonsMultipartFile[] files, Ticket ticket) {
        for (CommonsMultipartFile aFile : files) {
            if (aFile.getContentType() != null && !aFile.getContentType().equals("image/jpeg") &&
                    !aFile.getContentType().equals("image/png") && !aFile.getContentType().equals("application/pdf") &&
                    !aFile.getContentType().equals("application/msword") && !aFile.getContentType().equals("image/pjpeg") &&
                    !aFile.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            }

        }
        for (CommonsMultipartFile aFile : files) {
            Attachment attachment = new Attachment();
            attachment.setName(aFile.getOriginalFilename());
            attachment.setBlob(aFile.getBytes());
            attachment.setTicket(ticket);
            attachmentRepository.addAttachment(attachment);
            historyService.addAttachment(attachment);
        }
    }

    public void saveByTicketId(CommonsMultipartFile[] files, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        for (CommonsMultipartFile aFile : files) {
            Attachment attachment = new Attachment();
            attachment.setName(aFile.getOriginalFilename());
            attachment.setBlob(aFile.getBytes());
            attachment.setTicket(ticket);
            attachmentRepository.addAttachment(attachment);
            historyService.addAttachment(attachment);
        }
    }

    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.getAttachmentByTicketId(id);
    }

    public List<Attachment> getAttachmentsByTicketId(Long id) {
        return attachmentRepository.getAttachmentsByTicketId(id);
    }
}
