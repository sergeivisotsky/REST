package org.sergei.rest.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
    T findOne(final Long aLong);

    List<T> findAll();

    void save(final T entity);

    void update(final T entity);

    void delete(final T entity);
}
