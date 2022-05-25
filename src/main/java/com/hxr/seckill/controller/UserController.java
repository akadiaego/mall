package com.hxr.seckill.controller;


import com.hxr.seckill.pojo.User;
import com.hxr.seckill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangxinrui
 * @since 2022-04-26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);//brfore 818.3/sec
    }

}
