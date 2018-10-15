package org.sergei.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
    public Application() {
        packages("org.sergei.rest");
    }
}
