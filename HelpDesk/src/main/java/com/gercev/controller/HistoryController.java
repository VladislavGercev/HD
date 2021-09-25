package com.gercev.controller;

import com.gercev.converter.HistoryConverter;
import com.gercev.domain.History;
import com.gercev.domain.User;
import com.gercev.dto.HistoryDto;
import com.gercev.service.HistoryService;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryConverter historyConverter;

    @GetMapping("/histories/{id}")
    public HistoryDto getById(Principal principal, @PathVariable String id) {
        return historyConverter.convert(historyService.getHistoryById(Long.parseLong(id)));
    }

    @GetMapping("/tickets/{id}/histories")
    public ResponseEntity getHistoriesById(Principal principal, @PathVariable String id) {
        List<History> historyList = historyService.getHistoriesByTicketId(Long.parseLong(id));
        return ResponseEntity.ok(
                historyList.stream().map(historyConverter::convert).collect(Collectors.toList())
        );
    }
}
