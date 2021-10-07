package com.gercev.controller;

import com.gercev.converter.TicketConverter;
import com.gercev.domain.Ticket;
import com.gercev.domain.enums.State;
import com.gercev.dto.TicketDto;
import com.gercev.email.MailSender;
import com.gercev.service.CommentService;
import com.gercev.service.HistoryService;
import com.gercev.service.TicketService;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TicketConverter ticketConverter;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MailSender mailSender;

    @GetMapping
    public ResponseEntity<?> getTicketsByRole(Principal principal) {
        Optional<List<Ticket>> listTicketOptional = ticketService.getTicketsByUserRole(principal.getName());
        return listTicketOptional.map(tickets -> ResponseEntity.ok(tickets
                .stream()
                .map(ticketConverter::convert)
                .toArray())).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable("id") Long ticketId) {
        Optional<Ticket> ticketOptional = ticketService.getTicketById(ticketId);
        return ticketOptional
                .map(ticket -> ResponseEntity.ok(ticketConverter.convert(ticket)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody TicketDto ticketDto, Principal principal) {
        Optional<Ticket> ticketOptional = ticketService.addTicket(ticketDto, principal.getName());
        return ticketOptional.isPresent()
                ? new ResponseEntity<>(ticketConverter.convert(ticketOptional.get()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@RequestBody TicketDto ticketDto, Principal principal) {
        return ticketService.updateTicket(principal.getName(), ticketConverter.convert(ticketDto))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<?> changeTicketState(Principal principal, @PathVariable("id") Long ticketId,
                                               @PathVariable("state") State state) {
        return ticketService.updateTicketState(ticketId, state, principal.getName())
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
