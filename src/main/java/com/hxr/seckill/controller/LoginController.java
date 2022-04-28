package com.hxr.seckill.controller;

import com.hxr.seckill.service.IUserService;
import com.hxr.seckill.vo.LoginVo;
import com.hxr.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 * @author X.R.Huang
 * @since 1.0.0
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService userService;

    /**
     * 功能描述:跳转登陆页面
     *
     * @param
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 功能描述：登录功能
     * @param loginVo
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(LoginVo loginVo){
        log.info("{}",loginVo);
        return userService.doLogin(loginVo);
    }
}
