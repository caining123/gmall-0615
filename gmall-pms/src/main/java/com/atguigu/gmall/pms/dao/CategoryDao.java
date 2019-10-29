package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:18:18
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
