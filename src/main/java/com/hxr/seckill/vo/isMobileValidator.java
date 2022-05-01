package com.hxr.seckill.vo;

import com.hxr.seckill.utils.ValidatorUtil;
import com.hxr.seckill.validator.isMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验规则
 */
public class isMobileValidator implements ConstraintValidator<isMobile,String> {

    private boolean required = false;

    @Override
    public void initialize(isMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isMobile(value);
        }else{
            if (StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
