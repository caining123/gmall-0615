package com.atguigu.gmall.wms.dao;


import com.atguigu.gmall.wms.entity.FeightTemplateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 运费模板
 * 
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:42:51
 */
@Mapper
@Component
public interface FeightTemplateDao extends BaseMapper<FeightTemplateEntity> {
	
}
