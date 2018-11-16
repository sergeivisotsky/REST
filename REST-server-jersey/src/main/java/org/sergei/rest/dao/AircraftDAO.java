package org.sergei.rest.dao;

import org.sergei.rest.model.Aircraft;

/**
 * @author Sergei Visotsky, 2018
 */
@SuppressWarnings("unchecked")
public class AircraftDAO extends GenericHibernateDAO<Aircraft> {
    public AircraftDAO() {
        setPersistentClass(Aircraft.class);
    }
}
