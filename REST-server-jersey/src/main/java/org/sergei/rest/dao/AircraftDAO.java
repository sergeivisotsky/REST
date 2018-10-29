/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.rest.dao;

import org.sergei.rest.model.Aircraft;

@SuppressWarnings("unchecked")
public class AircraftDAO extends GenericHibernateDAO<Aircraft> {
    public AircraftDAO() {
        setPersistentClass(Aircraft.class);
    }
}
