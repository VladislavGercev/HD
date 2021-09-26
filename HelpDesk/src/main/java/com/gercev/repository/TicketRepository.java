package com.gercev.repository;

import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Optional<List<Ticket>> getAllTickets() {
        return Optional.of(sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket")
                .getResultList());
    }

    public Optional<List<Ticket>> getTicketsByUserId(long userId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("FROM Ticket WHERE owner.id =:ownerId")
                .setParameter("ownerId", userId)
                .getResultList());
    }

    public Optional<List<Ticket>> getTicketsForApprover(long approverId) {
        return Optional.of(sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE approver.id=:approverId or state in(:approved, :declined, :cancelled, :inProgress, :done) ")
                .setParameter("approverId", approverId)
                .setParameter("approved", State.APPROVED)
                .setParameter("declined", State.DECLINED)
                .setParameter("cancelled", State.CANCELED)
                .setParameter("inProgress", State.IN_PROGRESS)
                .setParameter("done", State.DONE)
                .getResultList());
    }


    public Optional<List<Ticket>> getTicketsForAssignee(long assigneeId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE assignee.id=:assigneeId and state in(:inProgress, :done)" +
                        "or (state=:approved)")
                .setParameter("assigneeId", assigneeId)
                .setParameter("inProgress", State.IN_PROGRESS)
                .setParameter("done", State.DONE)
                .setParameter("approved", State.APPROVED)
                .getResultList());
    }

    public Optional<List<Ticket>> getTicketsForManager(Long userId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE owner.id=:ownerId " +
                        "or (state=:new) " +
                        "or (approver.id=:approverId and state in(:approved, :declined, :cancelled, :inProgress, :done))")
                .setParameter("ownerId", userId)
                .setParameter("new", State.NEW)
                .setParameter("approverId", userId)
                .setParameter("approved", State.APPROVED)
                .setParameter("declined", State.DECLINED)
                .setParameter("cancelled", State.CANCELED)
                .setParameter("inProgress", State.IN_PROGRESS)
                .setParameter("done", State.DONE)
                .getResultList());
    }


    public Optional<Ticket> getTicketById(long ticketId) {
        return Optional.of((Ticket) sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE id=:id").setParameter("id", ticketId)
                .getSingleResult());
    }

    public boolean updateTicket(Ticket ticket) {
        try {
            sessionFactory.getCurrentSession().update(ticket);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateTicketState(Ticket ticket, State state) {
        try {
            sessionFactory.getCurrentSession()
                    .createQuery("UPDATE Ticket SET state=:state WHERE id=:id")
                    .setParameter("state", state)
                    .setParameter("id", ticket.getId())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean addApproverForTicket(Long id, User approver) {
        try {
            sessionFactory.getCurrentSession()
                    .createQuery("UPDATE Ticket SET approver.id=:approverId WHERE id=:id")
                    .setParameter("approverId", approver.getId())
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addAssigneeForTicket(Long id, User assignee) {
        try {
            sessionFactory.getCurrentSession()
                    .createQuery("UPDATE Ticket SET assignee.id=:assigneeId WHERE id=:id")
                    .setParameter("assigneeId", assignee.getId())
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Long> addTicket(Ticket ticket) {
        return Optional.of((long) sessionFactory.getCurrentSession().save(ticket));
    }
}
