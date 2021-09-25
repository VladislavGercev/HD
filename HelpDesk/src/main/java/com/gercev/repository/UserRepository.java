package com.gercev.repository;

import com.gercev.domain.Feedback;
import com.gercev.domain.User;
import com.gercev.domain.enums.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Optional<User> getUserByEmail(String email) {
//        return (User) sessionFactory.getCurrentSession()
//                .createQuery("from User where email = :email")
//                .setParameter("email", email).uniqueResult();
        try {
            return   Optional.of((User) sessionFactory.getCurrentSession()
                    .createQuery("FROM User WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<User> getManagers() {
        return (List<User>) sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE role = :role")
                .setParameter("role", Role.MANAGER).getResultList();
    }

    public List<User> getEngineers() {
        return (List<User>) sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE role = :role")
                .setParameter("role", Role.ENGINEER).getResultList();
    }
}
