package com.gercev.repository;

import com.gercev.domain.Category;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryRepository {

    @Autowired
    public SessionFactory sessionFactory;

    public Optional<Long> addCategory(Category category){
            return Optional.ofNullable((Long) sessionFactory.getCurrentSession().save(category));
    }

}
