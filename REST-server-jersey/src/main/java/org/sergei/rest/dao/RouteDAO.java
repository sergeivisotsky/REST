package org.sergei.rest.dao;

import org.sergei.rest.model.Route;

public class RouteDAO extends GenericHibernateDAO<Route> {
    public RouteDAO() {
        setPersistentClass(Route.class);
    }
}
