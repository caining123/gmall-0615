package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.AttrGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 属性分组
 *
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:18:19
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryByCidPage(Long catId, QueryCondition condition);

    AttrGroupVO queryById(Long gid);

    List<AttrGroupVO> queryGroupWithAttrsByCid(Long catId);
}

