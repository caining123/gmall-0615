package com.atguigu.gmall.sms.controller;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.entity.HomeAdvEntity;
import com.atguigu.gmall.sms.service.HomeAdvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 首页轮播广告
 *
 * @author caining
 * @email 1113885942@qq.ccom
 * @date 2019-10-29 06:31:23
 */
@Api(tags = "首页轮播广告 管理")
@RestController
@RequestMapping("sms/homeadv")
public class HomeAdvController {
    @Autowired
    private HomeAdvService homeAdvService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:homeadv:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = homeAdvService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:homeadv:info')")
    public Resp<HomeAdvEntity> info(@PathVariable("id") Long id){
		HomeAdvEntity homeAdv = homeAdvService.getById(id);

        return Resp.ok(homeAdv);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:homeadv:save')")
    public Resp<Object> save(@RequestBody HomeAdvEntity homeAdv){
		homeAdvService.save(homeAdv);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:homeadv:update')")
    public Resp<Object> update(@RequestBody HomeAdvEntity homeAdv){
		homeAdvService.updateById(homeAdv);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:homeadv:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		homeAdvService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}