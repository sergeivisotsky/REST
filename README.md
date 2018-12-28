# REST
[![Build Status](https://travis-ci.com/sergeivisotsky/REST.svg?branch=master)](https://travis-ci.com/sergeivisotsky/REST)

REST API server and client written using Spring Boot 2

## Technologies
* Java 8
* Spring Boot 2
* JPA
* Apache Maven
* MySQL
* ModelMapper
* Jackson
* Swagger

## TLS / SSL
Both REST-server and REST-client are using self-signed TLS/SSL PKCS12 certificate.

To generate this certificate the command `keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650` should be performed or you can use openssl also.

NOTE: Self-signed certificates are not verified by any certification agency and due to this every browser shows warning that they are not secured and consequently are not applicable for production and can be used for dev purposes only.


## Setup
Setup guides for each REST server and client are located in each modules' README.