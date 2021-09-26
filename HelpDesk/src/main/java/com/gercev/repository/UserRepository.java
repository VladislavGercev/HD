package com.gercev.repository;

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
        try {
            return Optional.of((User) sessionFactory.getCurrentSession()
                    .createQuery("FROM User WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<User>> getManagers() {
        return Optional.of(sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE role = :role")
                .setParameter("role", Role.MANAGER).getResultList());
    }

    public Optional<List<User>> getEngineers() {
        return Optional.of(sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE role = :role")
                .setParameter("role", Role.ENGINEER).getResultList());
    }
}
