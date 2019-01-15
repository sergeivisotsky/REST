/*
* NOTE: MySQL is preferable choice due to its SQL dialect is used in this script
*/
CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`%`
  SQL SECURITY DEFINER
  VIEW `rest_services`.`customer_report_view` AS
SELECT `c`.`customer_id`   AS `customer_id`,
       `c`.`first_name`    AS `first_name`,
       `c`.`last_name`     AS `last_name`,
       `o`.`order_id`      AS `order_id`,
       `o`.`order_date`    AS `order_date`,
       `o`.`required_date` AS `required_date`,
       `o`.`shipped_date`  AS `shipped_date`,
       `o`.`status`        AS `status`
FROM (`rest_services`.`customers` `c`
       JOIN `rest_services`.`orders` `o`)
WHERE (`o`.`customer_id` = `c`.`customer_id`)