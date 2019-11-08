package com.atguigu.gmall.wms.service;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.wms.entity.WareOrderTaskEntity;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 库存工作单
 *
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:42:51
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageVo queryPage(QueryCondition params);
}

