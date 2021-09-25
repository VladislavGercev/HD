package com.gercev.repository;

import com.gercev.domain.Ticket;
import com.gercev.domain.User;
import com.gercev.domain.enums.State;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Ticket> getAllTickets() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket")
                .getResultList();
    }

    public List<Ticket> getTicketsByUserId(long userId) {
        return sessionFactory.getCurrentSession().createQuery("from Ticket where owner.id =:ownerId")
                .setParameter("ownerId", userId)
                .getResultList();
    }

    public List<Ticket> getTicketsForApprover(long approverId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE approver.id=:approverId or state in(:approved, :declined, :cancelled, :inProgress, :done) ")
                .setParameter("approverId", approverId)
                .setParameter("approved", State.APPROVED)
                .setParameter("declined", State.DECLINED)
                .setParameter("cancelled", State.CANCELED)
                .setParameter("inProgress", State.IN_PROGRESS)
                .setParameter("done", State.DONE)
                .getResultList();
    }


    public List<Ticket> getTicketsForAssignee(long assigneeId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE assignee.id=:assigneeId and state in(:inProgress, :done)" +
                        "or (state=:approved)")
                .setParameter("assigneeId", assigneeId)
                .setParameter("inProgress", State.IN_PROGRESS)
                .setParameter("done", State.DONE)
                .setParameter("approved", State.APPROVED)
                .getResultList();
    }
//
//    public List<Ticket> getTicketsByManagerId(long managerId) {
//        return sessionFactory.getCurrentSession()
//                .createQuery("FROM ticket WHERE owner.id=:ownerId " +
//                        "or (approverId=:approverId and state=:approved ORDER BY urgency, desiredResolutionDate")
//                .setParameter("ownerId", managerId)
//                .setParameter("approverId", managerId)
//                .setParameter("approved", State.APPROVED)
//                .getResultList();
//    }

    public List<Ticket> getTicketsForManager(Long userId) {
        return sessionFactory.getCurrentSession()
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
                .getResultList();
    }

    public void updateTicket(Ticket ticket) {
        sessionFactory.getCurrentSession().update(ticket);
    }


    public Ticket getTicketById(long ticketId) {
        return (Ticket) sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE id=:id").setParameter("id", ticketId)
                .getSingleResult();
    }

    public void updateTicketState(Ticket ticket, State state) {
        sessionFactory.getCurrentSession()
                .createQuery("update Ticket SET state=:state where id=:id")
                .setParameter("state", state)
                .setParameter("id", ticket.getId())
                .executeUpdate();
    }

    public void addApproverForTicket(Long id, User approver) {
        sessionFactory.getCurrentSession()
                .createQuery("update Ticket set approver.id=:approverId where id=:id")
                .setParameter("approverId", approver.getId())
                .setParameter("id", id)
                .executeUpdate();
    }

    public void addAssigneeForTicket(Long id, User assignee) {
        sessionFactory.getCurrentSession()
                .createQuery("update Ticket set assignee.id=:assigneeId where id=:id")
                .setParameter("assigneeId", assignee.getId())
                .setParameter("id", id)
                .executeUpdate();
    }

    public long addTicket(Ticket ticket) {
        return (long) sessionFactory.getCurrentSession().save(ticket);
    }
}
