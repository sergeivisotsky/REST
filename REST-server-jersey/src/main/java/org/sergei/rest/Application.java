package org.sergei.rest;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Sergei Visotsky, 2018
 */
public class Application extends ResourceConfig {
    public Application() {
        register(new ApplicationBinder());
        packages("org.sergei.rest");
    }
}
