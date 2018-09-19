package org.sergei.rest.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.CLIENT_ID;

/**
 * TODO: Authentication using oAuth2 in Swagger UI
 * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String AUTH_SERVER = "http://localhost:8080/rest/oauth/token";
    private static final String CLIENT_SECRET = "client_secret";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER, "access_token"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER, CLIENT_ID, CLIENT_ID))
                .build();

        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "read operations"),
                new AuthorizationScope("write", "write operations"),
                new AuthorizationScope("trust", "trust operations")};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/api.*"))
                .build();
    }
}
