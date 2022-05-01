package com.hxr.seckill.vo;

import com.hxr.seckill.validator.isMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 登陆参数
 * @author X.R.Huang
 */
@Data
public class LoginVo {
    @NotNull
    @isMobile
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;
}
