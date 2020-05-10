CREATE SCHEMA `internet-shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet-shop.product` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
  );