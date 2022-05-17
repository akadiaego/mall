package com.hxr.seckill.validator;


import com.hxr.seckill.vo.isMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {isMobileValidator.class}
)
public @interface isMobile {

    boolean required() default true;
    ////如果出错，返回的数据
    String message() default "手机号码格式错误";
    //groups可以指定注解使用的场景，一个实体类可能会在多个场合有使用，如插入，删除等。通过groups可以指定该注解在插入/删除的环境下生效。
    Class<?>[] groups() default {};
    //payload往往对bean进行使用。
    Class<? extends Payload>[] payload() default {};
}
