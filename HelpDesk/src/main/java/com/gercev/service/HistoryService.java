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
import java.util.Optional;

@Service
@Transactional
public class HistoryService {
    private static final String TICKET_IS_CREATED = "Ticket is created";
    private static final String TICKET_IS_EDITED = "Ticket is edited";
    private static final String TICKET_STATUS_IS_CHANGED = "Ticket status is changed";
    private static final String FILE_IS_ATTACHED = "File is attached";
    private static final String FILE_IS_REMOVED = "File is removed";

    @Autowired
    private HistoryRepository historyRepository;

    public Optional<History> getHistoryById(long historyId) {
        return historyRepository.getHistoryById(historyId);
    }

    public Optional<List<History>> getHistoriesByTicketId(long ticketId) {
        return historyRepository.getHistoryByTicketId(ticketId);
    }

    public Optional<Long> addCreateTicketHistory(Ticket ticket, User user) {
        return historyRepository.addHistory(
                prepareHistory(ticket, user,
                        TICKET_IS_CREATED,
                        TICKET_IS_CREATED));
    }

    public boolean addTicketEditHistory(Ticket ticket, User user) {
        if (ticket.getState() == State.NEW) {
            addStatusChangedHistory(ticket, user);
            return true;
        } else {
            historyRepository.addHistory(
                    prepareHistory(ticket, user,
                            TICKET_IS_EDITED,
                            TICKET_IS_EDITED));
            return true;
        }
    }


    public Optional<Long> addUpdateTicketStatusHistory(Ticket ticket, User user, State stateFrom, State stateTo) {
        return historyRepository.addHistory(
                prepareHistory(ticket, user,
                        TICKET_STATUS_IS_CHANGED,
                        String.format(TICKET_STATUS_IS_CHANGED +
                                " from '%s' to '%s'", stateFrom.name(), stateTo.name())));
    }

    private void addStatusChangedHistory(Ticket ticket, User user) {
        historyRepository.addHistory(
                prepareHistory(ticket, user,
                        TICKET_STATUS_IS_CHANGED,
                        TICKET_STATUS_IS_CHANGED
                                + " from 'DRAFT' to 'NEW'"));
    }

    public void addAttachment(Attachment attachment) {
        historyRepository.addHistory(
                prepareHistory(attachment,
                        FILE_IS_ATTACHED,
                        String.format(FILE_IS_ATTACHED
                                + " : %s", attachment.getName())));
    }

    public void removeAttachment(Attachment attachment) {
        historyRepository.addHistory(
                prepareHistory(attachment,
                        FILE_IS_REMOVED,
                        String.format(FILE_IS_REMOVED
                                + " : %s", attachment.getName())));
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
