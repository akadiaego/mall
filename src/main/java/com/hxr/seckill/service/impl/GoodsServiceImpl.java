package com.hxr.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxr.seckill.mapper.GoodsMapper;
import com.hxr.seckill.pojo.Goods;
import com.hxr.seckill.service.IGoodsService;
import com.hxr.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxinrui
 * @since 2022-05-16
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 获取商品列表
     * @return
     */
    @Override
    public List<GoodsVo> findsGoodsVo() {
        return goodsMapper.findGoodsVo();
    }
}
