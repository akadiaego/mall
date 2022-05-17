CREATE TABLE `seckill_goods`(
                                `id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
                                `goods_id` BIGINT (20) DEFAULT NULL COMMENT '商品ID',
                                `seckill_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '秒杀价',
                                `stock_count` INT(10) DEFAULT NULL COMMENT '库存数量',
                                `start_date` DATETIME DEFAULT NULL COMMENT '秒杀开始时间',
                                `end_date` DATETIME DEFAULT NULL COMMENT '秒杀结束时间',
                                PRIMARY KEY(`id`)
)ENGINE = INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO `seckill_goods`VALUES(1,1,'5299',10,'2021-11-1 00:00:00','2021-11-11 01:00:00'),(2,2,'8299',10,'2021-11-11 00:00:00','2021-11-11 01:00:00');
