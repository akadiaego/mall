package com.hxr.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxr.seckill.pojo.Orders;
import com.hxr.seckill.pojo.SeckillOrders;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IGoodsService;
import com.hxr.seckill.service.IOrdersService;
import com.hxr.seckill.service.ISeckillOrdersService;
import com.hxr.seckill.vo.GoodsVo;
import com.hxr.seckill.vo.RespBean;
import com.hxr.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrdersService seckillOrdersService;

    @Autowired
    private IOrdersService ordersService;

    @RequestMapping("/doSecKill")
    public String doSecKill(Model model, User user,Long goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount()<1){
            model.addAttribute("errMsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        //getOne方法最终得到的是 实体类对象，其结果可以通过getXXXX()方法获取对象值；
        SeckillOrders seckillOrders = seckillOrdersService.getOne(new QueryWrapper<SeckillOrders>()
                .eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrders!=null){
            model.addAttribute("errMsg",RespBeanEnum.REPAET_ERROR.getMessage());
            return "secKillFail";
        }
        Orders order = ordersService.seckill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "ordersDetail";
    }

}
