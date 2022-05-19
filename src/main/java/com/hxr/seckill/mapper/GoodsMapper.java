package com.hxr.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxr.seckill.pojo.Goods;
import com.hxr.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     *
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @return
     * @param goodsId
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
