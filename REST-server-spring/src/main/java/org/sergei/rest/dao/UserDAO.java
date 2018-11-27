package org.sergei.rest.dao;

import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class UserDAO extends AbstractJpaHibernateDAO<User> {

    public UserDAO() {
        setPersistentClass(User.class);
    }

    public User findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u  WHERE u.username = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
