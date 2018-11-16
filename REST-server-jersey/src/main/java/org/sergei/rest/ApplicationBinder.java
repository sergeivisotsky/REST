package org.sergei.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.sergei.rest.dao.AircraftDAO;
import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.dao.RouteDAO;
import org.sergei.rest.service.AircraftService;
import org.sergei.rest.service.CustomerService;
import org.sergei.rest.service.RouteService;

/**
 * @author Sergei Visotsky, 2018
 */
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CustomerDAO.class).to(CustomerDAO.class);
        bind(CustomerService.class).to(CustomerService.class);
        bind(AircraftDAO.class).to(AircraftDAO.class);
        bind(AircraftService.class).to(AircraftService.class);
        bind(RouteDAO.class).to(RouteDAO.class);
        bind(RouteService.class).to(RouteService.class);
    }
}
