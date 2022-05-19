package com.hxr.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxr.seckill.mapper.OrdersMapper;
import com.hxr.seckill.pojo.Orders;
import com.hxr.seckill.pojo.SeckillGoods;
import com.hxr.seckill.pojo.SeckillOrders;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IOrdersService;
import com.hxr.seckill.service.ISeckillGoodsService;
import com.hxr.seckill.service.ISeckillOrdersService;
import com.hxr.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrdersService seckillOrdersService;
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    @Override
    public Orders seckill(User user, GoodsVo goods) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        seckillGoodsService.updateById(seckillGoods);
        //生成订单
        Orders order = new Orders();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        ordersMapper.insert(order);
        //生成秒杀订单
        SeckillOrders seckillOrders = new SeckillOrders();
        seckillOrders.setUserId(user.getId());
        seckillOrders.setOrderId(order.getId());
        seckillOrders.setGoodsId(goods.getId());
        seckillOrdersService.save(seckillOrders);
        return order;
    }
}
