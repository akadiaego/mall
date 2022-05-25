package com.hxr.seckill.controller;

import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IGoodsService;
import com.hxr.seckill.service.IUserService;
import com.hxr.seckill.vo.DetailVo;
import com.hxr.seckill.vo.GoodsVo;
import com.hxr.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转到商品列表页
     * @param model
     * @param user
     * @param model

     * @return
     */
    //public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8",method = RequestMethod.GET)
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        //redis获取页面，不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        //return "goodsList";
        //为空手动渲染存入redis并返回
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goodsList",webContext);
        if (!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }
        return html;
    }
    @RequestMapping(value = "/toDetail2/{goodsId}",produces = "text/html;charset=utf-8",method = RequestMethod.GET)
    @ResponseBody
    public String toDetail2(Model model,User user, @PathVariable Long goodsId,
                           HttpServletRequest request,HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = ((String) valueOperations.get("goodsDetail:" + goodsId));
        if (!StringUtils.isEmpty(html)){
            return html;
        }
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
        //为空手动渲染存入redis并返回
        WebContext webContext = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",webContext);
        if (!StringUtils.isEmpty(html)){
            valueOperations.set("goodsDetail:"+goodsId,html,60,TimeUnit.SECONDS);
        }
        return html;
        //return "goodsDetail";
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId){
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
        DetailVo detailVo = new DetailVo();
        detailVo.setGoodsVo(goodsVo);
        detailVo.setUser(user);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);

        return RespBean.success(detailVo);
    }
}
