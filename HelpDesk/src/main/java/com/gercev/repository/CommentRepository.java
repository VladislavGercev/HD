package com.gercev.repository;


import com.gercev.domain.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<List<Comment>> getCommentsByTicketId(long ticketId) {
        try {
            return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("FROM Comment WHERE ticket.id=:id")
                    .setParameter("id", ticketId).getResultList());
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Optional<Long> addComment(Comment comment) {
        return Optional.ofNullable((Long) sessionFactory.getCurrentSession()
                .save(comment));
    }
}
