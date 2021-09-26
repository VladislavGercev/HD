package com.gercev.repository;

import com.gercev.domain.History;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HistoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<History> getHistoryById(long historyId) {
        return (Optional<History>) sessionFactory.getCurrentSession().createQuery("FROM History WHERE id=:id")
                .setParameter("id", historyId).getSingleResult();
    }

    public Optional<List<History>> getHistoryByTicketId(long ticketId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("FROM History WHERE ticket.id=:id")
                .setParameter("id", ticketId).getResultList());
    }

    public Optional<Long> addHistory(History history) {
        return Optional.of((Long) sessionFactory.getCurrentSession().save(history));
    }
}
