DROP DATABASE IF EXISTS web_services;
CREATE DATABASE web_services /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS web_services.customers;
CREATE TABLE web_services.customers (
  customer_id bigint AUTO_INCREMENT UNIQUE NOT NULL,
  first_name  VARCHAR(50),
  last_name   VARCHAR(50),
  age         INTEGER,
  PRIMARY KEY (customer_id)
);

DROP TABLE IF EXISTS web_services.orders;
CREATE TABLE web_services.orders (
  order_id       BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id    BIGINT NOT NULL,
  trans_id       BIGINT NOT NULL,
  product        VARCHAR(50),
  product_weight DOUBLE PRECISION,
  price          DOUBLE PRECISION,
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id)
  REFERENCES web_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);

DROP TABLE IF EXISTS web_services.photos;
CREATE TABLE web_services.photos
(
  photo_id    BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  file_name   VARCHAR(100),
  file_url    VARCHAR(150),
  file_type   VARCHAR(50),
  file_size   integer,
  PRIMARY KEY (photo_id),
  FOREIGN KEY (customer_id)
  REFERENCES web_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);