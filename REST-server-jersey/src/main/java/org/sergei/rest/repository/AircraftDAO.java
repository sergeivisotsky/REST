package org.sergei.rest.repository;

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
