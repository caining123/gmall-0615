package com.atguigu.gmall.wms.service;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.wms.entity.WareInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 仓库信息
 *
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:42:51
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageVo queryPage(QueryCondition params);
}

