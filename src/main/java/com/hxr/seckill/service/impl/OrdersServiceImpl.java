package com.hxr.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxr.seckill.mapper.OrdersMapper;
import com.hxr.seckill.pojo.Orders;
import com.hxr.seckill.service.IOrdersService;
import org.springframework.stereotype.Service;

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

}
