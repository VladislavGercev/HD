package com.gercev.repository;

import com.gercev.domain.Feedback;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FeedbackRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Feedback> getFeedbackByTicketId(long ticketId) {
        try {
          return   Optional.of((Feedback) sessionFactory.getCurrentSession()
                    .createQuery("FROM Feedback WHERE ticket.id=:id")
                    .setParameter("id", ticketId)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public long addFeedback(Feedback feedback) {
        return (long) sessionFactory.getCurrentSession().save(feedback);
    }
}
