package com.gercev.repository;

import com.gercev.domain.Category;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoryRepository {

    @Autowired
    public SessionFactory sessionFactory;

    public long addCategory(Category category){
         return (long)sessionFactory.getCurrentSession().save(category);
    }

}
