package com.gercev.service;

import com.gercev.domain.Attachment;
import com.gercev.domain.Ticket;
import com.gercev.exception.AttachmentIsNotCreatedException;
import com.gercev.exception.TicketNotFoundException;
import com.gercev.repository.AttachmentRepository;
import com.gercev.util.builder.AttachmentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TicketService ticketService;


    public Optional<Long> addAttachment(CommonsMultipartFile[] files, Long ticketId) throws AttachmentIsNotCreatedException {
        Ticket ticket = ticketService.getTicketById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
        if (files != null) {
            for (CommonsMultipartFile aFile : files) {
                if (!fileVerification(aFile)) {
                    Attachment attachment = new AttachmentBuilder()
                            .setName(aFile.getOriginalFilename())
                            .setBlob(aFile.getBytes())
                            .setTicket(ticket)
                            .builder();
                    attachment.setId(attachmentRepository.addAttachment(attachment)
                            .orElseThrow(() -> new AttachmentIsNotCreatedException("Attachment for ticket " + ticketId + "isn't created")));
                    historyService.addAttachment(attachment);
                    return Optional.of(attachment.getId());
                }
            }
        }
        return Optional.empty();
    }

    private boolean fileVerification(CommonsMultipartFile file) {
        return (file.getContentType() != null && !file.getContentType().equals("image/jpeg") &&
                !file.getContentType().equals("image/png") && !file.getContentType().equals("application/pdf") &&
                !file.getContentType().equals("application/msword") && !file.getContentType().equals("image/pjpeg") &&
                !file.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    public Optional<Attachment> getAttachmentById(Long id) {
        return attachmentRepository.getAttachmentById(id);
    }

    public Optional<List<Attachment>> getAttachmentsByTicketId(Long id) {
        return attachmentRepository.getAttachmentsByTicketId(id);
    }
}
