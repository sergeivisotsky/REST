CREATE DATABASE IF NOT EXISTS `rest_services_jersey` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

CREATE TABLE IF NOT EXISTS `rest_services_jersey`.`customers` (
  `customer_number` BIGINT NOT NULL,
  `first_name` CHARACTER VARYING(50),
  `last_name` CHARACTER VARYING(50),
  `age` INTEGER,
  PRIMARY KEY(`customer_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;