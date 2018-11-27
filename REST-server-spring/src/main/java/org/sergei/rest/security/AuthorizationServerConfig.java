package org.sergei.rest.security;

import org.sergei.rest.service.ApiUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @author Sergei Visotsky, 2018
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final UserApprovalHandler userApprovalHandler;
    private final TokenStore tokenStore;
    private final ApiUserDetailsService apiUserDetailsService;

    @Autowired
    public AuthorizationServerConfig(DataSource dataSource, UserApprovalHandler userApprovalHandler,
                                     @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                     TokenStore tokenStore, ApiUserDetailsService apiUserDetailsService) {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
        this.userApprovalHandler = userApprovalHandler;
        this.tokenStore = tokenStore;
        this.apiUserDetailsService = apiUserDetailsService;
    }

    // All the clients are stored into the database
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(apiUserDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.realm("API_REALM");
    }
}