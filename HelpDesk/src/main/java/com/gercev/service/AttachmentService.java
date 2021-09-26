package com.gercev.service;

import com.gercev.domain.Attachment;
import com.gercev.domain.Ticket;
import com.gercev.repository.AttachmentRepository;
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


    public boolean addAttachment(CommonsMultipartFile[] files, Long ticketId) {
        Optional<Ticket> ticketOptional = ticketService.getTicketById(ticketId);
        if (files != null && ticketOptional.isPresent()) {
            for (CommonsMultipartFile aFile : files) {
                if (!fileVerification(aFile)) {
                    Attachment attachment = new Attachment();
                    attachment.setName(aFile.getOriginalFilename());
                    attachment.setBlob(aFile.getBytes());
                    attachment.setTicket(ticketOptional.get());
                    attachmentRepository.addAttachment(attachment);
                    historyService.addAttachment(attachment);
                }
            }
        }
        return true;
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
