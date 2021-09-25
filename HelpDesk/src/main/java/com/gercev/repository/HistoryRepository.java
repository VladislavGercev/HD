package com.gercev.repository;

import com.gercev.domain.History;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public History getHistoryById(long historyId) {
        return (History) sessionFactory.getCurrentSession().createQuery("FROM History WHERE id=:id")
                .setParameter("id", historyId).getSingleResult();
    }
    public List<History> getHistoryByTicketId(long ticketId) {
        return  sessionFactory.getCurrentSession().createQuery("FROM History WHERE ticket.id=:id")
                .setParameter("id", ticketId).getResultList();
    }

    public long addHistory(History history){
         return (long) sessionFactory.getCurrentSession().save(history);
    }
}
