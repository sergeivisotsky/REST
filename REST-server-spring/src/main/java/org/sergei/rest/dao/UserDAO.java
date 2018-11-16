package org.sergei.rest.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.sergei.rest.dao.generic.GenericHibernateDAO;
import org.sergei.rest.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class UserDAO extends GenericHibernateDAO<User> {

    public UserDAO() {
        setPersistentClass(User.class);
    }

    public User findByUserName(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.like("username", username));
        return (User) criteria.uniqueResult();
    }

    /*public User findByUserName(String username) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, username);
        session.close();
        return user;
    }*/
}
