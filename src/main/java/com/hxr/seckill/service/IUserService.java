package com.hxr.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.vo.LoginVo;
import com.hxr.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-04-26
 */
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    /**
     * 更新密码
     * @param userTicket
     * @param id
     * @param password
     * @return
     */
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request, HttpServletResponse response);
}
