package com.gercev.service;

import com.gercev.domain.Attachment;
import com.gercev.domain.History;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import com.gercev.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public History getHistoryById(long historyId) {
        return historyRepository.getHistoryById(historyId);
    }

    public List<History> getHistoriesByTicketId(long ticketId) {
        return historyRepository.getHistoryByTicketId(ticketId);
    }

    public Long addCreateTicketHistory(Ticket ticket, User user) {
        return historyRepository.addHistory(
                prepareHistory(ticket, user,
                        "Ticket is created",
                        "Ticket is created"));
    }

    public Long addEditTicketHistory(Ticket ticket, User user) {
        if (ticket.getState() == State.NEW) {
            addStatusChangedHistory(ticket, user);
        }
        return historyRepository.addHistory(
                prepareHistory(ticket, user,
                        "Ticket is edited",
                        "Ticket is edited"));
    }

    public Long addUpdateTicketStatusHistory(Ticket ticket, User user,State stateFrom, State stateTo) {
        return historyRepository.addHistory(
                prepareHistory(ticket, user,
                        "Ticket status is changed",
                        String.format("Ticket status is changed from '%s' to '%s'", stateFrom.name(), stateTo.name())));
    }

    private void addStatusChangedHistory(Ticket ticket, User user) {
        historyRepository.addHistory(
                prepareHistory(ticket, user,
                        "Ticket status is changed",
                        "Ticket status is changed from 'DRAFT' to 'NEW'"));
    }

    public void addAttachment(Attachment attachment) {
        historyRepository.addHistory(
                prepareHistory(attachment,
                        "File is attached",
                        String.format("File is attached: %s", attachment.getName())));
    }

    public void removeAttachment(Attachment attachment) {
        historyRepository.addHistory(
                prepareHistory(attachment,
                        "File is removed",
                        String.format("File is removed: %s", attachment.getName())));
    }

    private History prepareHistory(Ticket ticket, User user, String action, String description) {
        History history = new History();
        history.setDate(LocalDate.now());
        history.setTicket(ticket);
        history.setUser(user);
        history.setDescription(action);
        history.setAction(description);
        return history;
    }

    private History prepareHistory(Attachment attachment, String action, String description) {
        History history = new History();
        history.setDate(LocalDate.now());
        history.setTicket(attachment.getTicket());
        history.setUser(attachment.getTicket().getOwner());
        history.setAction(action);
        history.setDescription(description);
        return history;
    }
}
