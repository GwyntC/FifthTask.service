DROP DATABASE IF EXISTS products;
CREATE DATABASE products;

USE products;
CREATE TABLE `categories` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(100) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `products` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `model_name` varchar(255) NOT NULL,
                            `brand_name` varchar(45) NOT NULL,
                            `country` varchar(255) NOT NULL,
                            `price` decimal(15,2) NOT NULL,
                            `category_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `category_id` (`category_id`),
                            FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchProducts`(IN brandName VARCHAR(255),IN categoryName VARCHAR(255),IN offsetPage int,IN count int)
BEGIN
select p.id, p.model_name, p.brand_name,p.country,p.price,p.category_id from products p
inner join categories c on c.id=p.category_id
where p.brand_name = brandName and c.name = categoryName
ORDER BY p.id
LIMIT offsetPage,count;
END$$
DELIMITER ;

INSERT INTO `categories` VALUES ('laptops'),
                                ('keyboards');

INSERT INTO `products` VALUES
                           ('Asus Zenbook 14 UM425QA-KI198 Pine Grey','ASUS','China',816.63,1),
                           ('Asus X515JA-BR3853W Grey','ASUS','China',626.08,1),
                           ('Lenovo IdeaPad 1 15ADA7 (82R10047RA) Cloud Grey','LENOVO','China',598.84,1),
                           ('Lenovo IdeaPad Gaming 3 15ACH6 (82K201B9RA) Shadow Black','LENOVO','China',816.60,1),
                           ('Acer Nitro 5 AN515-57-51H7 (NH.QEKEU.002) Black','ACER','China',871.05,1),
                           ('Acer Aspire 3 A315-56 (NX.HS5EU.01J) Shale Black','ACER','China',476.34,1),
                           ('REAL-EL Comfort 7090 Backlit USB Black','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB White','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Green','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Pink','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Red','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Orange','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Blue','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Yellow','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Metallic','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Purple','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Marble','REAL-EL','Estonia Republic',70.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Gold','REAL-EL','Estonia Republic',60.00,2),
                           ('REAL-EL Comfort 7090 Backlit USB Platina','REAL-EL','Estonia Republic',90.20,2),
                           ('Acer Aspire 3 A315-56 (NX.HS5EU.01J) Shale White','ACER','China',476.34,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) White','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Black','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Black','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Golden','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Golden','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Silver','HP','China',891.62,1),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Silver','HP Pavilion','Estonia',180.06,2),
                           ('HP Pavilion Aero 13-be0027ua (5A5Z1EA) Silver','Rudenko','Kazakhstan',180.09,1);