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

    public boolean updateAttachment(Attachment attachment) {
        try {
            sessionFactory.getCurrentSession()
                    .saveOrUpdate(attachment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void remove(Attachment attachment) {
        sessionFactory.getCurrentSession()
                .remove(attachment);
    }

    public Optional<List<Attachment>> getAttachmentsByTicketId(Long ticketId) {
        try {
            return Optional.ofNullable(sessionFactory.getCurrentSession()
                    .createQuery("FROM Attachment WHERE ticket.id=:ticketId")
                    .setParameter("ticketId", ticketId)
                    .getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Attachment> getAttachmentById(Long id) {
        return Optional.ofNullable((Attachment) sessionFactory.getCurrentSession()
                .createQuery("FROM Attachment WHERE id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public boolean deleteAttachment(Long id) {
        try {
            sessionFactory.getCurrentSession().createQuery("DELETE Attachment WHERE id = :id")
                    .setParameter("id", id).executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
