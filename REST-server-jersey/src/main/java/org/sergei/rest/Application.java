/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
    public Application() {
        register(new ApplicationBinder());
        packages("org.sergei.rest");
    }
}
