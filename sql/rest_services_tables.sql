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

DROP TABLE IF EXISTS rest_services.products;
CREATE TABLE rest_services.products (
  product_id     BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  category      VARCHAR(50),
  product_name   VARCHAR(50),
  product_weight FLOAT,
  product_price  FLOAT,
  PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS rest_services.orders;
CREATE TABLE rest_services.orders (
  order_id    BIGINT AUTO_INCREMENT UNIQUE NOT NULL,
  customer_id BIGINT NOT NULL,
  product_id   BIGINT NOT NULL,
  trans_id    BIGINT NOT NULL,
  total_price FLOAT,
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (product_id)
  REFERENCES rest_services.products (product_id)
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
  file_size   INTEGER,
  PRIMARY KEY (photo_id),
  FOREIGN KEY (customer_id)
  REFERENCES rest_services.customers (customer_id)
  ON UPDATE NO ACTION ON DELETE CASCADE
);


SELECT
       orders.order_id, orders.customer_id, orders.product_id, orders.trans_id, orders.total_price,
       products.category, products.product_name, products.product_weight, products.product_price
FROM rest_services.orders
         AS orders LEFT JOIN rest_services.products as products ON orders.product_id = products.product_id
UNION ALL
SELECT
       orders.order_id, orders.customer_id, orders.product_id, orders.trans_id, orders.total_price,
       products.category, products.product_name, products.product_weight, products.product_price
FROM rest_services.orders
         AS orders RIGHT JOIN rest_services.products as products ON orders.product_id = products.product_id