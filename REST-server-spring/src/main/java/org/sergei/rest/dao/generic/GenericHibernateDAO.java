package org.sergei.rest.dao.generic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class GenericHibernateDAO<T extends Serializable> implements IGenericDAO<T> {

    private Class<T> persistentClass;

    public final void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public T findOne(Long aLong) {
        return getCurrentSession().get(persistentClass, aLong);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + persistentClass.getName()).list();
    }

    @Override
    public void save(T entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
