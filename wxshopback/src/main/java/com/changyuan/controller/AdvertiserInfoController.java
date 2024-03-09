package com.changyuan.controller;

import com.changyuan.common.Result;
import com.changyuan.entity.AdvertiserInfo;
import com.changyuan.service.AdvertiserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 公告增删改查控制器
 */
@RestController
@RequestMapping(value = "/advertiserInfo")
public class AdvertiserInfoController {

    @Resource
    private AdvertiserInfoService advertiserInfoService;

    /**
     * 分页查询公告
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param name     公告名
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<AdvertiserInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
        return Result.success(advertiserInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 新增公告
     */
    @PostMapping
    public Result<AdvertiserInfo> add(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.add(advertiserInfo);
        return Result.success(advertiserInfo);
    }

    /**
     * 更新公告
     */
    @PutMapping
    public Result<AdvertiserInfo> update(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.update(advertiserInfo);
        return Result.success();
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        advertiserInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条公告
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(advertiserInfoService.findById(id));
    }
}

