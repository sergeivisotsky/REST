CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`%`
  SQL SECURITY DEFINER
  VIEW `rest_services`.`customer_report_view` AS
SELECT
  `c`.`customer_id` AS `customer_id`,
  `c`.`first_name` AS `first_name`,
  `c`.`last_name` AS `last_name`,
  `o`.`order_id` AS `order_id`,
  `o`.`order_date` AS `order_date`,
  `o`.`required_date` AS `required_date`,
  `o`.`shipped_date` AS `shipped_date`,
  `o`.`status` AS `status`,
  `od`.`price` AS `price`,
  `od`.`quantity_ordered` AS `quantity_ordered`,
  `od`.`product_code` AS `product_code`
FROM
  ((`rest_services`.`customers` `c`
    JOIN `rest_services`.`orders` `o`)
    JOIN `rest_services`.`order_details` `od`)
WHERE
  ((`o`.`customer_id` = `c`.`customer_id`)
    AND (`o`.`order_id` = `od`.`order_id`))