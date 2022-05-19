package com.hxr.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxr.seckill.pojo.Goods;
import com.hxr.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */

    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
