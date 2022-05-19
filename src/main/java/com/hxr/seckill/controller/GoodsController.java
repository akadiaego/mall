package com.hxr.seckill.controller;

import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IGoodsService;
import com.hxr.seckill.service.IUserService;
import com.hxr.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;


    /**
     * 跳转到商品列表页
     * @param model
     * @param user
     * @param model

     * @return
     */

    @RequestMapping("/toList")
    //public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){
    public String toList(Model model, User user){

//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
//        if (user == null){
//            return "login";
//        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        return "goodsList";
    }
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model,User user, @PathVariable Long goodsId){
        //@PathVariable 映射 URL 绑定的占位符
        //带占位符的 URL 是 Spring3.0 新增的功能，
        // 该功能在SpringMVC 向 REST 目标挺进发展过程中具有里程碑的意义
        //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中：
        // URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx“) 绑定到操作方法的入参中。
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀未开始
        if (nowDate.before(startDate)){
            remainSeconds = ((int)((startDate.getTime() - nowDate.getTime())/1000));
        }else if (nowDate.after(endDate)){
            //秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        }else{
            //秒杀中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("user",user);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("goods",goodsVo);
        return "goodsDetail";
    }
}
