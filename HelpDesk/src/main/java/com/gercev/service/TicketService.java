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

    public Optional<Long> addTicket(TicketDto ticketDto, CommonsMultipartFile[] files, String email) {
        Ticket ticket = ticketConverter.convert(ticketDto);
        Optional<User> userOptional = userService.getByEmail(email);
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        ticket.setOwner(user);
        ticket.setCreatedOn(LocalDate.now());
        Optional<Long> ticketIdOptional = ticketRepository.addTicket(ticket);
        if (!ticketIdOptional.isPresent()) {
            return Optional.empty();
        }
        ticket.setId(ticketIdOptional.get());

        categoryRepository.addCategory(ticket.getCategory());
        if (files != null) {
            attachmentService.addAttachment(files, ticket.getId());
        }
        Comment comment = commentConverter.convert(ticketDto.getComment());
        commentService.addComment(comment, ticket.getId(), email);
        if (ticket.getState() == State.NEW) {
            Optional<List<User>> listManagersOptional = userService.getManagers();
            listManagersOptional.ifPresent(users -> mailSender.sendAcceptableEmail(users, ticket.getId(), EmailTemplate.NEW_TICKET_FOR_APPROVAL));
        }
        return ticketIdOptional;
    }

    public Optional<List<Ticket>> getTicketsByUserRole(String email) {
        Optional<User> userOptional = userService.getByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
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
        } else return Optional.empty();
    }

    public boolean update(String email, Ticket ticket) {
        Optional<User> userOptional = userService.getByEmail(email);
        if (userOptional.isPresent()) {
            historyService.addTicketEditHistory(ticket, userOptional.get());
            return ticketRepository.updateTicket(ticket);
        }
        return false;
    }

    public boolean changeTicketState(Long id, State stateTo, String email) {
        Optional<User> userOptional = userService.getByEmail(email);
        Optional<Ticket> ticketOptional = ticketRepository.getTicketById(id);
        if (userOptional.isPresent() && ticketOptional.isPresent()) {
            User user = userOptional.get();
            Ticket ticket = ticketOptional.get();
            State stateFrom = ticket.getState();
            List<User> recipients = getRecipients(ticket, stateFrom, stateTo);
            if (checkState(user, ticket, stateTo)) {
                ticketRepository.updateTicketState(ticket, stateTo);
                switch (stateTo) {
                    case NEW:
                        mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.NEW_TICKET_FOR_APPROVAL);
                    case APPROVED:
                        ticketRepository.addApproverForTicket(ticket.getId(), user);
                        mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.TICKET_WAS_APPROVED);
                        break;
                    case CANCELED:
                        if (stateFrom == State.NEW) {
                            mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.TICKET_WAS_CANCELLED_MANAGER);
                        }
                        if (stateFrom == State.APPROVED) {
                            mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.TICKET_WAS_CANCELLED_ENGINEER);
                        }
                        break;
                    case DECLINED:
                        mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.TICKET_WAS_DECLINED);
                        break;
                    case IN_PROGRESS:
                        ticketRepository.addAssigneeForTicket(ticket.getId(), user);
                        break;
                    case DONE:
                        mailSender.sendAcceptableEmail(recipients, id, EmailTemplate.TICKET_WAS_DONE);
                }
                historyService.addUpdateTicketStatusHistory(ticket, user, stateFrom, stateTo);
                return true;
            }
        }
        return false;
    }


    private List<User> getRecipients(Ticket ticket, State stateFrom, State stateTo) {
        List<User> recipients = new ArrayList<>();
        User owner = ticket.getOwner();
        switch (stateTo) {
            case NEW:
                Optional<List<User>> listManagersOptional = userService.getManagers();
                listManagersOptional.ifPresent(recipients::addAll);
                break;
            case APPROVED:
                Optional<List<User>> listEngineersOptional = userService.getEngineers();
                if (listEngineersOptional.isPresent()) {
                    recipients.addAll(listEngineersOptional.get());
                    recipients.add(owner);
                }
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


    private boolean checkState(User user, Ticket ticket, State stateNew) {
        boolean result = false;
        switch (stateNew) {
            case NEW:
                if (ticket.getState() == State.DRAFT || ticket.getState() == State.DECLINED) {
                    if ((user.getRole() == Role.EMPLOYEE || user.getRole() == Role.MANAGER)
                            && ticket.getOwner().getId() == user.getId()) {
                        result = true;
                    }
                }
                break;
            case APPROVED:
            case DECLINED:
                if (ticket.getState() == State.NEW) {
                    if (user.getRole() == Role.MANAGER && ticket.getOwner().getId() != user.getId()) {
                        result = true;
                    }
                }
                break;
            case CANCELED:
                if (ticket.getState() == State.DRAFT || ticket.getState() == State.DECLINED) {
                    if ((user.getRole() == Role.EMPLOYEE || user.getRole() == Role.MANAGER)
                            && ticket.getOwner().getId() == user.getId()) {
                        result = true;
                    }
                }
                if (ticket.getState() == State.NEW) {
                    if (user.getRole() == Role.MANAGER && ticket.getOwner().getId() != user.getId()) {
                        result = true;
                    }
                }
                if (ticket.getState() == State.APPROVED) {
                    if (user.getRole() == Role.ENGINEER) {
                        result = true;
                    }
                }
            case IN_PROGRESS:
                if (ticket.getState() == State.APPROVED) {
                    if (user.getRole() == Role.ENGINEER) {
                        result = true;
                    }
                }
            case DONE:
                if (ticket.getState() == State.IN_PROGRESS) {
                    if (user.getRole() == Role.ENGINEER) {
                        result = true;
                    }
                }
        }
        return result;
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


