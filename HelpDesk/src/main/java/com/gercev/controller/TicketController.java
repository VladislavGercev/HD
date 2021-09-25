package com.gercev.controller;

import com.gercev.converter.TicketConverter;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import com.gercev.dto.TicketDto;
import com.gercev.email.MailSender;
import com.gercev.exception.UserNotFoundException;
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
import java.util.Optional;

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
    public ResponseEntity getTicketsByRole(Principal principal) {
        Optional<User> userOptional = userService.getByEmail(principal.getName());
        User user = userOptional
                .orElseThrow(() -> new UserNotFoundException("User not found" + principal));
        return ResponseEntity.ok(ticketService.getTicketsByUserRole(user)
                .stream()
                .map(ticketConverter::convert)
                .toArray());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTicketById(@PathVariable String id, Principal principal) {
        return ResponseEntity.ok(ticketConverter.convert(ticketService.getTicketById(Long.parseLong(id))));
    }

    @PostMapping
    public ResponseEntity addTicket(@RequestParam(value = "files", required = false) CommonsMultipartFile[] files,
                                    @RequestParam(value = "ticketDTO") String ticketJson, Principal principal) {
        return ResponseEntity.ok(ticketConverter.convert(
                ticketService.addTicket(ticketConverter.convert(ticketJson), files, principal.getName())
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity editTicket(Principal principal, @RequestBody TicketDto ticketDTO) {
        ticketService.update(principal.getName(), ticketConverter.convert(ticketDTO));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity changeTicketState(Principal principal, @PathVariable("id") long id, @PathVariable("state") State state) {
        ticketService.changeTicketState(id, state, principal.getName());
        return ResponseEntity.ok("ok");
    }
}
