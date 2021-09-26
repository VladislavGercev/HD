package com.gercev.controller;

import com.gercev.converter.HistoryConverter;
import com.gercev.domain.History;
import com.gercev.service.HistoryService;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryConverter historyConverter;

    @GetMapping("/histories/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable("id") Long ticketId) {
        Optional<History> historyOptional = historyService.getHistoryById(ticketId);
        return historyOptional
                .map(history -> ResponseEntity.ok(historyConverter.convert(history)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/tickets/{id}/histories")
    public ResponseEntity<?> getHistoriesByTicketId(@PathVariable("id") Long ticketId) {
        Optional<List<History>> historyListOptional = historyService.getHistoriesByTicketId(ticketId);
        return historyListOptional
                .map(histories -> ResponseEntity.ok(histories
                        .stream()
                        .map(historyConverter::convert)
                        .toArray()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
