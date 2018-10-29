/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
public abstract class GenericHibernateDAO<T extends Serializable> {

    private static final String PERSISTENCE_UNIT = "org.sergei.rest.jpa";

    private Class<T> persistentClass;

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    public T findOne(Long aLong) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        T entity = entityManager.find(persistentClass, aLong);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    public List<T> findAll() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<T> entityList = entityManager.createQuery("FROM " + persistentClass.getName()).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityList;
    }

    public void save(T entity) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(T entity) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(T entity) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(entity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    protected void finalize() {
        if (factory != null) {
            factory.close();
        }
    }
}
