DROP DATABASE IF EXISTS rest_services_dev;
CREATE DATABASE rest_services_dev /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS rest_services_dev.customers;
CREATE TABLE rest_services_dev.customers (
  customer_id bigint AUTO_INCREMENT UNIQUE NOT NULL,
  first_name  VARCHAR(50),
  last_name   VARCHAR(50),
  age         INTEGER,
  PRIMARY KEY (customer_id)
);

DROP TABLE IF EXISTS rest_services_dev.products;
CREATE TABLE rest_services_dev.products (
  product_id     bigint AUTO_INCREMENT UNIQUE NOT NULL,
  product_name   VARCHAR(50),
  category       VARCHAR(50),
  product_weight FLOAT,
  price          decimal(10, 2),
  PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS rest_services_dev.orders;
CREATE TABLE rest_services_dev.orders (
  order_id    BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  product_id  BIGINT NOT NULL,
  trans_id    BIGINT NOT NULL,
  total_price decimal(10, 2),
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services_dev.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (product_id)
  REFERENCES rest_services_dev.products (product_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);

DROP TABLE IF EXISTS rest_services_dev.photos;
CREATE TABLE rest_services_dev.photos
(
  photo_id    BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  file_name   VARCHAR(100),
  file_url    VARCHAR(150),
  file_type   VARCHAR(50),
  file_size   integer,
  PRIMARY KEY (photo_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services_dev.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);