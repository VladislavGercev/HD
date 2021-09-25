package com.gercev.repository;


import com.gercev.domain.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Comment> getCommentsByTicketId(long ticketId) {
        return sessionFactory.getCurrentSession().createQuery("FROM Comment WHERE ticket.id=:id")
                .setParameter("id", ticketId).getResultList();
    }

    public Long addComment(Comment comment) {
        return (Long) sessionFactory.getCurrentSession()
                .save(comment);
    }
}
