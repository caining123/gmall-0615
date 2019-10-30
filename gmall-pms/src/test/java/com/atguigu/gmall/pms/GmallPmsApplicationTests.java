package com.atguigu.gmall.pms;

import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmallPmsApplicationTests {
    @Autowired
    BrandService brandService;
    @Test
    void contextLoads() {
    }



    @Test
    public void test(){
        IPage<BrandEntity> page = this.brandService.page(new Page<BrandEntity>(2, 2), new QueryWrapper<BrandEntity>());
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
    }
}
