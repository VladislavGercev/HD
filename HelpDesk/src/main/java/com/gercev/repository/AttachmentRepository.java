package com.gercev.repository;

import com.gercev.domain.Attachment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttachmentRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void addAttachment(Attachment attachment){
        sessionFactory.getCurrentSession()
                .save(attachment);
    }
    public void removeAttachment(Attachment attachment){
        sessionFactory.getCurrentSession()
                .remove(attachment);
    }
    public List<Attachment> getAttachmentsByTicketId(Long ticketId){
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Attachment WHERE ticket.id=:ticketId")
                .setParameter("ticketId",ticketId)
                .getResultList();
    }
    public Attachment getAttachmentByTicketId(Long id){
        return (Attachment) sessionFactory.getCurrentSession()
                .createQuery("FROM Attachment WHERE id=:id")
                .setParameter("id",id)
                .getSingleResult();
    }
}
