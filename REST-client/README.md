# REST-client
Client of the RESTful service

## Technologies
* Java 8
* Apache Maven
* Spring Boot 2
* Thymeleaf

## TLS / SSL
Resource is using self-signed TLS/SSL PKCS12 certificate.

To generate this certificate the command `keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650` should be performed or you can use openssl also.

NOTE: Self-signed certificates are not verified by any certification agency and due to this every browser shows warning that they are not secured and consequently are not applicable for production and can be used for dev purposes only.


## Setup
1. Change `oauth.username` and `oauth.password` properties according to yours username and password in `application.yml` config file
2. Open `logback-spring.xml` setup directory where all your logging files are going to saved

## Run
* Perform command `mvn spring-boot:run` or compile project in .jar and perform command `java -jar target/rest-client-VERSION.jar`