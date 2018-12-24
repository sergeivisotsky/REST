# REST-server
REST API implementation using Spring Boot

## Technologies
* Java 8
* Spring Boot 2
* JPA
* Apache Maven
* MySQL
* ModelMapper
* Jackson
* Swagger

For api documentation is used Swagger which is accessible by the url - `http://localhost:8080/docs` which redirects to the `http://localhost:8080/swagger-ui.html`.

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

Client ID and client secret should be provided in headers as a basic auth.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `username=USERNAME&password=PASSWORD&grant_type=GRANT_TYPE`

_response_
```
{
    "access_token": "29ff004b-98d8-4127-85a7-286913cff240",
    "token_type": "bearer",
    "refresh_token": "e1d442ce-f681-465e-b267-044ead90f67b",
    "expires_in": 57594,
    "scope": "read trust write"
}
```

After authentication it is able to access any resource with an access token provided.

The next step is get an access to the resources. Example: `http://localhost:8080/api/v2/customers?access_token=ACCESS_TOKEN`

In case if access token is expired refresh token should be used to renew access token.

* URL: `http://localhost:8080/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `grant_type=refresh_token&refresh_token=REFRESH_TOKEN`