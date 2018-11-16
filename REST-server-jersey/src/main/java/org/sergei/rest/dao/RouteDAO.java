package org.sergei.rest.dao;

import org.sergei.rest.model.Route;

/**
 * @author Sergei Visotsky, 2018
 */
public class RouteDAO extends GenericHibernateDAO<Route> {
    public RouteDAO() {
        setPersistentClass(Route.class);
    }
}
