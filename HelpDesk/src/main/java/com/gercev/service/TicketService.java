package com.gercev.service;

import com.gercev.converter.CommentConverter;
import com.gercev.converter.TicketConverter;
import com.gercev.domain.Comment;
import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.Role;
import com.gercev.domain.enums.State;
import com.gercev.dto.TicketDto;
import com.gercev.email.EmailTemplate;
import com.gercev.email.MailSender;
import com.gercev.exception.TicketIsNotCreatedException;
import com.gercev.exception.TicketNotFoundException;
import com.gercev.exception.UserNotFoundException;
import com.gercev.repository.CategoryRepository;
import com.gercev.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private TicketConverter ticketConverter;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentConverter commentConverter;
    @Autowired
    private MailSender mailSender;

    public TicketService() {
    }

    public Optional<Ticket> addTicket(TicketDto ticketDto, CommonsMultipartFile[] files, String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + "NotFound"));
        Ticket ticket = ticketConverter.convert(ticketDto);
        ticket.setOwner(user);
        ticket.setCreatedOn(LocalDate.now());
        ticket.setId(ticketRepository.addTicket(ticket)
                .orElseThrow(() -> new TicketIsNotCreatedException("Ticket isn't created")));
        categoryRepository.addCategory(ticket.getCategory());
        if (files != null) {
            attachmentService.addAttachment(files, ticket.getId());
        }
        Comment comment = commentConverter.convert(ticketDto.getComment());
        commentService.addComment(comment, ticket.getId(), email);
        if (ticket.getState() == State.NEW) {
            mailSender.sendAcceptableEmail(userService.getManagers()
                            .orElseThrow(() -> new UserNotFoundException("Managers are not Found")),
                    ticket.getId(), EmailTemplate.NEW_TICKET_FOR_APPROVAL);
        }
        return Optional.of(ticket);

    }

    public Optional<List<Ticket>> getTicketsByUserRole(String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + "NotFound"));
        switch (user.getRole()) {
            case EMPLOYEE:
                return ticketRepository.getTicketsByUserId(user.getId());
            case MANAGER:
                return ticketRepository.getTicketsForManager(user.getId());
            case ENGINEER:
                return ticketRepository.getTicketsForAssignee(user.getId());
            default:
                return Optional.empty();
        }
    }

    public boolean updateTicket(String email, TicketDto ticketDto) {
        Ticket ticket = ticketConverter.convert(ticketDto);
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + "NotFound"));
        historyService.addTicketEditHistory(ticket, user);
        return ticketRepository.updateTicket(ticket);
    }

    public boolean updateTicketState(Long ticketId, State stateTo, String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + "NotFound"));
        Ticket ticket = ticketRepository.getTicketById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
        State stateFrom = ticket.getState();
        List<User> recipients = getRecipients(ticket, stateFrom, stateTo);
        if (ticketUpdateValidation(ticket, stateTo, user.getRole())) {
            ticketRepository.updateTicketState(ticket, stateTo);
            switch (stateTo) {
                case NEW:
                    mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.NEW_TICKET_FOR_APPROVAL);
                case APPROVED:
                    ticketRepository.addApproverForTicket(ticketId, user);
                    mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.TICKET_WAS_APPROVED);
                    break;
                case CANCELED:
                    if (stateFrom == State.NEW) {
                        mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.TICKET_WAS_CANCELLED_MANAGER);
                    }
                    if (stateFrom == State.APPROVED) {
                        mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.TICKET_WAS_CANCELLED_ENGINEER);
                    }
                    break;
                case DECLINED:
                    mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.TICKET_WAS_DECLINED);
                    break;
                case IN_PROGRESS:
                    ticketRepository.addAssigneeForTicket(ticketId, user);
                    break;
                case DONE:
                    mailSender.sendAcceptableEmail(recipients, ticketId, EmailTemplate.TICKET_WAS_DONE);
            }
            historyService.addUpdateTicketStatusHistory(ticket, user, stateFrom, stateTo);
            return true;
        }
        return false;
    }


    private List<User> getRecipients(Ticket ticket, State stateFrom, State stateTo) {
        List<User> recipients = new ArrayList<>();
        User owner = ticket.getOwner();
        switch (stateTo) {
            case NEW:
                recipients.addAll((userService.getManagers()
                        .orElseThrow(() -> new UserNotFoundException("Managers are not found"))));
                break;
            case APPROVED:
                recipients.addAll(userService.getEngineers()
                        .orElseThrow(() -> new UserNotFoundException("Engineers are not found")));
                recipients.add(owner);
                break;
            case DECLINED:
            case DONE:
                recipients.add(owner);
                break;
            case CANCELED:
                if (stateFrom == State.NEW) {
                    recipients.add(owner);
                } else {
                    recipients.add(owner);
                    recipients.add(ticket.getApprover());
                }
                break;
        }
        return recipients;
    }

    private boolean ticketUpdateValidation(Ticket ticket, State state, Role role) {
        switch (role) {
            case EMPLOYEE:
                return ticketUpdateValidationEmployee(ticket, state);
            case MANAGER:
                return ticketUpdateValidationManager(ticket, state);
            case ENGINEER:
                return ticketUpdateValidationEngineer(ticket, state);
            default:
                return false;
        }
    }

    private boolean ticketUpdateValidationEmployee(Ticket ticket, State state) {
        return ((ticket.getState() == State.DRAFT || ticket.getState() == State.DECLINED) &&
                (state == State.NEW || state == State.CANCELED));

    }

    private boolean ticketUpdateValidationManager(Ticket ticket, State state) {
        return ((ticket.getState() == State.NEW && ticket.getOwner().getRole() == Role.EMPLOYEE)
                && (state == State.APPROVED || state == State.DECLINED) || state == State.CANCELED);
    }

    private boolean ticketUpdateValidationEngineer(Ticket ticket, State state) {
        return ((ticket.getState() == State.APPROVED &&
                (state == State.IN_PROGRESS || state == State.CANCELED)) ||
                (ticket.getState() == State.IN_PROGRESS && state == State.DONE));
    }

    public Optional<List<Ticket>> getTicketsByUserId(long id) {
        return ticketRepository.getTicketsByUserId(id);
    }

    public Optional<List<Ticket>> getTicketsByApproverId(long id) {
        return ticketRepository.getTicketsForApprover(id);
    }

    public Optional<List<Ticket>> getTicketsByAssigneeId(long id) {
        return ticketRepository.getTicketsForAssignee(id);
    }

    public Optional<Ticket> getTicketById(long id) {
        return ticketRepository.getTicketById(id);
    }
}


