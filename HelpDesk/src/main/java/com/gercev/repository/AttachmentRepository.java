package com.gercev.repository;

import com.gercev.domain.Attachment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Long> addAttachment(Attachment attachment) {
            return Optional.ofNullable((Long) sessionFactory.getCurrentSession()
                    .save(attachment));
    }

    public void remove(Attachment attachment) {
        sessionFactory.getCurrentSession()
                .remove(attachment);
    }

    public Optional<List<Attachment>> getAttachmentsByTicketId(Long ticketId) {
            return Optional.ofNullable(sessionFactory.getCurrentSession()
                    .createQuery("FROM Attachment WHERE ticket.id=:ticketId")
                    .setParameter("ticketId", ticketId)
                    .getResultList());
    }

    public Optional<Attachment> getAttachmentById(Long id) {
            return Optional.ofNullable((Attachment) sessionFactory.getCurrentSession()
                    .createQuery("FROM Attachment WHERE id=:id")
                    .setParameter("id", id)
                    .getSingleResult());
    }
}
