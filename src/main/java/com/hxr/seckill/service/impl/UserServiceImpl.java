package com.hxr.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxr.seckill.exception.GlobalException;
import com.hxr.seckill.mapper.UserMapper;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.service.IUserService;
import com.hxr.seckill.utils.MD5Util;
import com.hxr.seckill.utils.ValidatorUtil;
import com.hxr.seckill.vo.LoginVo;
import com.hxr.seckill.vo.RespBean;
import com.hxr.seckill.vo.RespBeanEnum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-04-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 登录校验
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (user==null){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
