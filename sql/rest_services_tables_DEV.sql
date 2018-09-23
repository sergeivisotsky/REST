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

DROP TABLE IF EXISTS rest_services_dev.orders;
CREATE TABLE rest_services_dev.orders (
  order_id      BIGINT NOT NULL,
  customer_id   BIGINT NOT NULL,
  order_date DATE NOT NULL,
  required_date DATE   NOT NULL,
  shipped_date  DATE DEFAULT NULL,
  `status` varchar (15
) NOT NULL,
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services_dev.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);

DROP TABLE IF EXISTS rest_services_dev.products;
CREATE TABLE rest_services_dev.products (
  product_code  VARCHAR(15) NOT NULL,
  product_name  VARCHAR(70) NOT NULL,
  product_line   varchar(50) NOT NULL,
  product_vendor varchar(50) NOT NULL,
  price         decimal(10, 2),
  PRIMARY KEY (product_code)
);

DROP TABLE IF EXISTS rest_services_dev.order_details;
CREATE TABLE rest_services_dev.order_details (
  order_id     BIGINT         NOT NULL,
  product_code VARCHAR(15)    NOT NULL,
  quantity     INT (30) NOT NULL,
  price        DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (order_id)
  REFERENCES rest_services_dev.orders (order_id)
  ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (product_code)
  REFERENCES rest_services_dev.products (product_code)
  ON UPDATE NO ACTION ON DELETE CASCADE
);