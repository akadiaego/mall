package com.hxr.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登录模块
    LOGIN_ERROR(500210,"用户名密码错误"),
    MOBILE_ERROR(500211,"手机号格式不正确");
    //公共返回对象枚举
    private final Integer code;
    private final String message;


}
