/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.swagger;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SwaggerConfig extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080/rest-jersey");
        beanConfig.setBasePath("/api/v1");
        beanConfig.setResourcePackage("org.sergei.rest.controller");
        beanConfig.setScan(true);
    }
}
