package org.sergei.rest.dao.generic;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractJpaHibernateDAO<T extends Serializable> implements IAbstractJpaDAO<T> {

    private Class<T> persistentClass;

    @Override
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T findOne(Long aLong) {
        return entityManager.find(persistentClass, aLong);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + persistentClass.getName()).getResultList();
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
