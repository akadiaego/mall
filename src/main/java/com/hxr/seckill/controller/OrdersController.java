package com.hxr.seckill.controller;


import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IOrdersService;
import com.hxr.seckill.vo.OrderDetailVo;
import com.hxr.seckill.vo.RespBean;
import com.hxr.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    /**
     * 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user,Long orderId){
        if(user== null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = ordersService.detail(orderId);
        return RespBean.success(detail);
    }

}
