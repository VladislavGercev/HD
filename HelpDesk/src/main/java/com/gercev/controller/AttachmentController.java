package com.gercev.controller;

import com.gercev.converter.AttachmentConverter;
import com.gercev.domain.Attachment;
import com.gercev.dto.TicketDto;
import com.gercev.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentConverter attachmentConverter;

    @PostMapping(value = "tickets/{id}/attachments")
    public ResponseEntity<TicketDto> addAttachmentsByTicketId(@RequestParam(name = "files") CommonsMultipartFile[] files,
                                                              @PathVariable Long id) {
        attachmentService.saveByTicketId(files, id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "tickets/{ticketId}/attachments/{id}")
    public ResponseEntity getAttachment(@PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok().header(HttpHeaders.LINK,
                "attachment: filename-\"" + attachment.getName() + "\"").body(attachment.getBlob());
    }

    @GetMapping(value = "tickets/{id}/attachments")
    public ResponseEntity getAttachmentsByTicketId(@PathVariable Long id) {
        return ResponseEntity.ok(attachmentService.getAttachmentsByTicketId(id).stream()
                .map(attachmentConverter::convert).toArray());
    }
}
