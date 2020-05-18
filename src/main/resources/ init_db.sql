CREATE SCHEMA `internet-shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet-shop'.'product` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE `internet-shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `login` VARCHAR(225) NOT NULL,
  `password` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC)
);

CREATE TABLE `internet-shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `orders_users_fk_idx` (`user_id` ASC),
  CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE `internet-shop`.`orders_product` (
    `order_id` BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `order_order_idx` (`order_id` ASC),
    INDEX `order_product_idx` (`product_id` ASC),
    CONSTRAINT `order_order`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet-shop`.`orders` (`order_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT `order_product`
            FOREIGN KEY (`product_id`)
                REFERENCES `internet-shop`.`product` (`product_id`)
                ON DELETE CASCADE
                ON UPDATE CASCADE
);

CREATE TABLE `internet-shop`.`roles` (
    `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(225) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC)
);

INSERT INTO roles(role_name)
VALUES ('ADMIN');
INSERT INTO roles(role_name)
VALUES ('USER');

CREATE TABLE `internet-shop`.`user_roles` (
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `user_user_idx` (`user_id` ASC),
    INDEX `user_role_idx` (`role_id` ASC),
    CONSTRAINT `user_role_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet-shop`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `user_role_role`
        FOREIGN KEY (`role_id`)
        REFERENCES `internet-shop`.`roles` (`role_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE `internet-shop`.`shopping_cart` (
    `shopping_cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`shopping_cart_id`),
    INDEX `shopping_cart_user_idx` (`user_id` ASC),
    CONSTRAINT `shopping_cart_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet-shop`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE `internet-shop`.`shopping_carts_products` (
    `cart_id` BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `cart_product_idx` (`product_id` ASC),
    INDEX `cart_cart_idx` (`cart_id` ASC),
    CONSTRAINT `cart_product`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet-shop`.`product` (`product_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `shopping_cart_cart`
        FOREIGN KEY (`cart_id`)
            REFERENCES `internet-shop`.`shopping_cart` (`shopping_cart_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

ALTER TABLE `internet-shop`.`users`
    ADD COLUMN `salt` VARBINARY(400) NOT NULL AFTER `password`,
    ADD UNIQUE INDEX `salt_UNIQUE` (`salt` ASC);
;

