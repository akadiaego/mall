create table `t_goods`(
`id` BIGINT(20) not null AUTO_INCREMENT COMMENT '商品id',
`goods_name` VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
`goods_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
`goods_img` VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
`goods_detail` LONGTEXT  COMMENT '商品描述',
`goods_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品价格',
`goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存,-1表示没有限制',
PRIMARY KEY(`id`)
)ENGINE = INNODB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4;


1.p64的HandlerMethod（）应该导入以下包，不然进不去if (handler instanceof HandlerMethod) {} 判断中，注解会无效 import org.springframework.web.method.HandlerMethod;
2.在页面静态化时，注意data.obj 这个值，可能会不生效，可以使用data.object
3.安装redis时注意设置密码，我之前学习redis课程时，6379端口直接被入侵木马
4.lua脚本很容易出现书写错误，导致使用脚本报错
5.MQ报错出现长时间异常，RabbitMq 消费者监听生产者传来的对象出错Execution of Rabbit message listener failed. Listener threw exception ，可以直接在客户端页面删除错误的队列即可
6.在linux启动java -jar包时，出现端口占用，查看进程：netstat -lnp|grep 8080-》Kill -9 端口号

redis 5.0.14