package com.atguigu.gmall.pms.service;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * spu信息
 *
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:18:18
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo querySpuInfoByKeyPage(Long catId, QueryCondition condition);

    void bigSave(SpuInfoVO spuInfoVO);

}

