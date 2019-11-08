package com.atguigu.gmall.sms.dao;

import com.atguigu.gmall.sms.entity.CategoryBoundsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 商品分类积分设置
 * 
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:31:23
 */
@Mapper
@Component
public interface CategoryBoundsDao extends BaseMapper<CategoryBoundsEntity> {
	
}
