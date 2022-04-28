package com.hxr.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    //公共返回对象
    private long code;
    private String message;
    private Object obj;

    /**
     * 功能描述：成功返回结果
     * @param
     * @return
     */

    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(), null);
    }

    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(), obj);
    }
    /**
     * 功能描述：失败返回结果
     * @param
     * @return
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        //传枚举是因为失败各有不同
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum,Object obj){
        //传枚举是因为失败各有不同
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }

}
