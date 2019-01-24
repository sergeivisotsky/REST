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

To generate keystore and public certificate the following commands should be performed.

Command to generate keystore:
```text
keytool -genkey -alias KEYSTORE_ENTRY -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
```

Command to generate public certificate:
```text
keytool -exportcert -keystore keystore.p12 -storepass MY_PASSWORD -alias KEYSTORE_ENTRY -rfc -file public-certificate.pem
```

In case of self-signed certificate it should be added to the JVM cacerts so that client were able to communicate with services.

Command for operation described below:
```text
keytool -import -trustcacerts -keystore "%JAVA_HOME%/jre/lib/security/cacerts" -storepass changeit -alias KEYSTORE_ENTRY -import -file public-certificate.pem
```

* `changeit` default password fоr cacerts

You can use openssl also.

**__NOTE: Self-signed certificates are not verified by any certification agency and due to this every browser shows 
warning that they are not secured and consequently are not applicable for production and can be used for dev purposes only.__**

## Setup
Setup guides for each REST server and client are located in each modules' README.