/*
*
* BACKUP
*
 */

DROP DATABASE IF EXISTS rest_services;
CREATE DATABASE rest_services /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS rest_services.customers;
CREATE TABLE rest_services.customers (
  customer_id bigint AUTO_INCREMENT UNIQUE NOT NULL,
  first_name  VARCHAR(50),
  last_name   VARCHAR(50),
  age         INTEGER,
  PRIMARY KEY (customer_id)
);

DROP TABLE IF EXISTS rest_services.orders;
CREATE TABLE rest_services.orders (
  order_id       BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id    BIGINT NOT NULL,
  trans_id       BIGINT NOT NULL,
  product        VARCHAR(50),
  product_weight DOUBLE PRECISION,
  price          DOUBLE PRECISION,
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);

DROP TABLE IF EXISTS rest_services.photos;
CREATE TABLE rest_services.photos
(
  photo_id    BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  file_name   VARCHAR(100),
  file_url    VARCHAR(150),
  file_type   VARCHAR(50),
  file_size   integer,
  PRIMARY KEY (photo_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);

INSERT INTO rest_services.orders (order_id, customer_id, trans_id, product, product_weight, price) VALUES(1, 1, 2412354235, "car", 200, 250000);
INSERT INTO rest_services.orders (order_id, customer_id, trans_id, product, product_weight, price) VALUES(2, 1, 5236346, "cat", 1, 300);
INSERT INTO rest_services.orders (order_id, customer_id, trans_id, product, product_weight, price) VALUES(3, 1, 63246, "flowers", 2, 1.5);