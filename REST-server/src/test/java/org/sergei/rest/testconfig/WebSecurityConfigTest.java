package org.sergei.rest.testconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security config for test cases
 *
 * @author Sergei Visotsky
 * Created on 12/7/2018
 */
@Configuration
@EnableWebSecurity
@Order(99)
public class WebSecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/v1/**").permitAll();
    }
}