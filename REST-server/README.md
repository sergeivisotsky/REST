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

## API documentation
For endpoint documentation is used Swagger which is accessible by the url - `http://localhost:8080/docs` which redirects to the `http://localhost:8080/swagger-ui.html`.

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

Client ID and client secret should be provided in headers as a basic auth.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `username=USERNAME&password=PASSWORD&grant_type=GRANT_TYPE`

_Response:_
```json
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

## Setup
* Setup your database driver dependency.

_Example for MySQL:_
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

1. Open `application.yml` file and setup your database url and credentials
2. Change database name in `oauth_schem.sql`
3. To create view for customer report open file `customer_report_view.sql` and execute SQL code in your database (NOTE: MySQL dialect was used in this case)
4. Keep in mind that application port and port in `security.oauth2.resource.accessTokenUri` property might be changed in your case
5. Open `logback-spring.xml` setup directory where all your logging files are going to saved

## Run
* Perform command `mvn spring-boot:run` or compile project in .jar and perform command `java -jar target/REST-server-VERSION.jar`