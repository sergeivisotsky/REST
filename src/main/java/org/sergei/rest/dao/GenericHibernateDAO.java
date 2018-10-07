package org.sergei.rest.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public abstract class GenericHibernateDAO<T extends Serializable> {

    private Class<T> persistentClass;

    public final void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + persistentClass.getName()).getResultList();
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
