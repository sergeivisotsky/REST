package org.sergei.rest.repository;

import org.sergei.rest.model.Route;

/**
 * @author Sergei Visotsky, 2018
 */
public class RouteDAO extends GenericHibernateDAO<Route> {
    public RouteDAO() {
        setPersistentClass(Route.class);
    }
}
