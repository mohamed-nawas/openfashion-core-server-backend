SET
sql_mode = '';

CREATE TABLE `file`
(
    `id`      varchar(255) NOT NULL,
    `created` datetime DEFAULT NULL,
    `data`    longblob     NOT NULL,
    `name`    varchar(255) NOT NULL,
    `type`    varchar(255) NOT NULL,
    `updated` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product`
(
    `id`            varchar(255) NOT NULL,
    `created`       datetime     DEFAULT NULL,
    `description`   varchar(255) NOT NULL,
    `name`          varchar(255) NOT NULL,
    `price_per_qty` int          NOT NULL,
    `product_type`  varchar(255) DEFAULT NULL,
    `updated`       datetime     DEFAULT NULL,
    `image_id`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY             `FK8c3le2kkeulnl3uspx95hum0n` (`image_id`),
    CONSTRAINT `FK8c3le2kkeulnl3uspx95hum0n` FOREIGN KEY (`image_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_colors`
(
    `product_id` varchar(255) NOT NULL,
    `colors`     varchar(255) DEFAULT NULL,
    KEY          `FKrxtutgloy8nt7w2k50fnmu3ji` (`product_id`),
    CONSTRAINT `FKrxtutgloy8nt7w2k50fnmu3ji` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_sizes`
(
    `product_id` varchar(255) NOT NULL,
    `sizes`      varchar(255) DEFAULT NULL,
    KEY          `FK4w69qsh5hd062xv3hqkpgpdpu` (`product_id`),
    CONSTRAINT `FK4w69qsh5hd062xv3hqkpgpdpu` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;