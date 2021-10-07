package com.gercev.service;

import com.gercev.domain.Attachment;
import com.gercev.domain.History;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import com.gercev.exception.HistoryIsNotCreatedException;
import com.gercev.repository.HistoryRepository;
import com.gercev.util.builder.HistoryBuilder;
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

    public Optional<History> addCreateTicketHistory(Ticket ticket, User user) {

        try {
            History history = prepareHistory(ticket, user,
                    TICKET_IS_CREATED,
                    TICKET_IS_CREATED);
            history.setId(historyRepository.addHistory(history)
                    .orElseThrow(() -> new HistoryIsNotCreatedException(
                            "Create_History for ticket " + ticket.getId() + "isn't created")));
            return Optional.of(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<History> addTicketEditHistory(Ticket ticket, User user) {
        try {
            if (ticket.getState() == State.NEW) {
                return addStatusChangedHistoryFromDraftToNew(ticket, user);
            } else {
                History history = prepareHistory(ticket, user,
                        TICKET_IS_EDITED,
                        TICKET_IS_EDITED);
                history.setId(historyRepository.addHistory(history)
                        .orElseThrow(() -> new HistoryIsNotCreatedException(
                                "Ticket_change status History for ticket " + ticket.getId() + " isn't created")));
                return Optional.of(history);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<History> addUpdateTicketStatusHistory(Ticket ticket, User user, State stateFrom, State stateTo) {
        try {
            History history = prepareHistory(ticket, user,
                    TICKET_STATUS_IS_CHANGED,
                    String.format(TICKET_STATUS_IS_CHANGED +
                            " from '%s' to '%s'", stateFrom.name(), stateTo.name()));
            history.setId(historyRepository.addHistory(history).orElseThrow(() -> new HistoryIsNotCreatedException(
                    "Ticket_change status History for ticket " + ticket.getId() + " isn't created")));
            return Optional.of(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<History> addStatusChangedHistoryFromDraftToNew(Ticket ticket, User user) {
        try {
            History history = prepareHistory(ticket, user,
                    TICKET_STATUS_IS_CHANGED,
                    TICKET_STATUS_IS_CHANGED
                            + " from 'DRAFT' to 'NEW'");
            history.setId(historyRepository.addHistory(history)
                    .orElseThrow(() -> new HistoryIsNotCreatedException(
                            "Ticket_change status History for ticket " + ticket.getId() + " isn't created")));
            return Optional.of(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<History> addAttachment(Attachment attachment) {
        try {
            History history =
                    prepareHistory(attachment,
                            FILE_IS_ATTACHED,
                            String.format(FILE_IS_ATTACHED
                                    + " : %s", attachment.getName()));
            history.setId(historyRepository.addHistory(history)
                    .orElseThrow(() -> new HistoryIsNotCreatedException(
                            "Attachment_add History for " + attachment.getName() + " isn't created")));
            return Optional.of(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<History> removeAttachment(Attachment attachment) {
        try {
            History history = prepareHistory(attachment,
                    FILE_IS_REMOVED,
                    String.format(FILE_IS_REMOVED
                            + " : %s", attachment.getName()));
            history.setId(historyRepository.addHistory(history)
                    .orElseThrow(() -> new HistoryIsNotCreatedException(
                            "Attachment_remove History " + attachment.getName() + " isn't created")));
            return Optional.of(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private History prepareHistory(Ticket ticket, User user, String action, String description) {
        return new HistoryBuilder().setDate(LocalDate.now())
                .setTicket(ticket)
                .setUser(user)
                .setDescription(description)
                .setAction(action)
                .build();
    }

    private History prepareHistory(Attachment attachment, String action, String description) {
        return new HistoryBuilder().setDate(LocalDate.now())
                .setTicket(attachment.getTicket())
                .setUser(attachment.getTicket().getOwner())
                .setDescription(description)
                .setAction(action)
                .build();
    }
}
