package com.hxr.seckill.vo;

import com.hxr.seckill.pojo.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private Orders order;

    private GoodsVo goodsVo;


}
