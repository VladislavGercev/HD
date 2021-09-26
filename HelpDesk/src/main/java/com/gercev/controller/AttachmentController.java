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

import java.util.List;
import java.util.Optional;

@RestController
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentConverter attachmentConverter;

    @GetMapping(value = "tickets/{ticketId}/attachments/{id}")
    public ResponseEntity<?> getAttachment(@PathVariable("id") Long attachmentId) {
        Optional<Attachment> attachmentOptional = attachmentService.getAttachmentById(attachmentId);
        return attachmentOptional.isPresent() ?
                ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment: filename-\"" + attachmentOptional.get().getName() + "\"").body(attachmentOptional.get().getBlob())
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "tickets/{id}/attachments")
    public ResponseEntity<TicketDto> addAttachmentsByTicketId(@RequestParam(name = "files") CommonsMultipartFile[] files, @PathVariable("id") Long ticketId) {
        return attachmentService.addAttachment(files, ticketId)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "tickets/{id}/attachments")
    public ResponseEntity<?> getAttachmentsByTicketId(@PathVariable("id") Long ticketId) {
        Optional<List<Attachment>> attachmentsOptional = attachmentService.getAttachmentsByTicketId(ticketId);
        return attachmentsOptional.map(attachments -> ResponseEntity.ok(attachments
                .stream()
                .map(attachmentConverter::convert)
                .toArray())).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
