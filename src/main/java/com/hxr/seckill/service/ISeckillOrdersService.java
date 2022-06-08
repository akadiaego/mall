package com.hxr.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxr.seckill.pojo.SeckillOrders;
import com.hxr.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
public interface ISeckillOrdersService extends IService<SeckillOrders> {
    Long getResult(User user, Long goodsId);
}
