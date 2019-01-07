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

## TLS / SSL
Resource is using self-signed TLS/SSL PKCS12 certificate.

To generate this certificate the command `keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650` should be performed or you can use openssl also.

NOTE: Self-signed certificates are not verified by any certification agency and due to this every browser shows warning that they are not secured and consequently are not applicable for production and can be used for dev purposes only.

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

#### 1 way - using client credentials
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

#### 2 way - using _authorization_code_ (more secured)
1. Get authorization code sending credentials using method _GET_ in browser

`https://localhost:9090/auth-api/oauth/authorize?
&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&response_type=code&redirect_uri=REDIRECT_URI&scope=SCOPES`

2. Authorization with code:
Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `redirect_uri=REDIRECT_URI&grant_type=authorization_code&code=CODE&scope=SCOPES`

_Response:_
```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDY5NDYyMDEsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDA2Y2VmYi0xYjRlLTQxYzYtOGVlZi0xZDEzZDBhNGM2YjkiLCJjbGllbnRfaWQiOiJ0cnVzdC1tZSIsInNjb3BlIjpbInJlYWQiXX0.fpcwy8K9EUK07YAzX4QvGKM65jL7aV64lnJA3HiA2Y-EJphN-2L5HOM2MfOKtpROjtB0he0ZbUM75RWQGhpiODcf2mpvWRa1L466cnCPtoj6BN2Rdyi_ZcHG0HAtRRZHRkDfRfeVtBxl7N_AxceK3esuV-y-hD-sWq-XYi5vdt-yjdJdoGG8sJ4S9Ee_qz8qUt2baRKrpwn2dAFyD5peLRhIxKvjaVUWK6lOyVg2aoaWHADV1F-ALvxF63l40JLccu4Yqmoq7rDeFzfUn66KrHneTBwAFQPsB3cxBaFSsXUWblz-mKZbNG55V1y6huqGv-6ip3M9UvOrmigSEejF6Q",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiNTAwNmNlZmItMWI0ZS00MWM2LThlZWYtMWQxM2QwYTRjNmI5IiwiZXhwIjoxNTQ3MTE5MDAxLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIl0sImp0aSI6ImE2NzA5NjFmLTY3NGUtNGM3Ny1iOTY2LTg3YzM2ZDYyOTk0OSIsImNsaWVudF9pZCI6InRydXN0LW1lIn0.Cmda6hHop3WgCmdLhpKwbRhDVPxGu6huTHpWl-xicZ5WYSe4uDLC5bGH5h7ZDPGPk4VhDYgXYG-rbCk0bcPymbbzZa511idCP2BbAplFhYCRcaOw76pksc_4os-sF8nCFDXUU2x1eMkJeGYukSO_VxuU7KvYFiOQXGxSOvQgN_7P0fp-1wUwyx16DOqQGZWByr1mL0s0nJhEl-w7jhKgHIsKVa8STXj-bkAOi3dYkB9kkRV9110ZDRdqxI0e5CaZEgcpEd6jEdTAJgTiLH4K68eEiAOA-jNIbXr174J0PT__Pnwm16wM_XaV0diDqkkZWxwuw3dbKp7Gqg8El_xFjA",
    "expires_in": 86399,
    "scope": "read",
    "jti": "5006cefb-1b4e-41c6-8eef-1d13d0a4c6b9"
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
2. Change database name in `oauth_schema.sql`
3. To create view for customer report open file `customer_report_view.sql` and execute SQL code in your database (NOTE: MySQL dialect was used in this case)
4. Keep in mind that application port and port in `security.oauth2.resource.accessTokenUri` property might be changed in your case
5. Open `logback-spring.xml` setup directory where all your logging files are going to saved
6. Go to the class `PhotoService` and change the value of the final field `UPLOAD_DIR`

## Run
* Perform command `mvn spring-boot:run` or compile project in .jar and perform command `java -jar target/REST-server-VERSION.jar`