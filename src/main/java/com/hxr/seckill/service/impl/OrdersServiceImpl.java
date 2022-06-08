package com.hxr.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxr.seckill.exception.GlobalException;
import com.hxr.seckill.mapper.OrdersMapper;
import com.hxr.seckill.pojo.Orders;
import com.hxr.seckill.pojo.SeckillGoods;
import com.hxr.seckill.pojo.SeckillOrders;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IGoodsService;
import com.hxr.seckill.service.IOrdersService;
import com.hxr.seckill.service.ISeckillGoodsService;
import com.hxr.seckill.service.ISeckillOrdersService;
import com.hxr.seckill.vo.GoodsVo;
import com.hxr.seckill.vo.OrderDetailVo;
import com.hxr.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public Orders seckill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = stock_count -1").eq("goods_id",goods.getId()).gt("stock_count",0));
        //seckillGoodsService.updateById(seckillGoods);
//        if (!seckillGoodsResult){
//            return null;
//        }
        if (seckillGoods.getStockCount() < 1){
            //判断是否还有库存
            valueOperations.set("isStockEmpty:"+goods.getId(),"0");
            return null;
        }
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
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(),seckillOrders);
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null){
            throw new GlobalException(RespBeanEnum.REPEAT_ERROR);
        }
        Orders order = ordersMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }
}
