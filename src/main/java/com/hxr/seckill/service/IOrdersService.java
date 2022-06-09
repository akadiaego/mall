package com.hxr.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxr.seckill.pojo.Orders;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.vo.GoodsVo;
import com.hxr.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
public interface IOrdersService extends IService<Orders> {

    Orders seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId,String path);
}
