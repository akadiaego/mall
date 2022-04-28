package com.hxr.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.vo.LoginVo;
import com.hxr.seckill.vo.RespBean;

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
     * @return
     */
    RespBean doLogin(LoginVo loginVo);
}
