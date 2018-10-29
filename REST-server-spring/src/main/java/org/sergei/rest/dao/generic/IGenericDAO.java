/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.rest.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
    /**
     * Find one entity
     * @param aLong takes ID
     * @return returns entity
     */
    T findOne(final Long aLong);

    /**
     * Find all entities
     * @return all entities found
     */
    List<T> findAll();

    /**
     * Save one entity
     * @param entity takes entity from the RequestBody
     */
    void save(final T entity);

    /**
     * Updates entity taken from request body
     * @param entity entity taken from request body
     */
    void update(final T entity);

    /**
     * Deletes entity found in service layer
     * @param entity takes entity found
     */
    void delete(final T entity);
}
