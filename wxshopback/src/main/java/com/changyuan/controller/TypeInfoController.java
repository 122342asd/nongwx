package com.changyuan.controller;

import com.changyuan.common.Result;
import com.changyuan.entity.TypeInfo;
import com.changyuan.service.TypeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品类别增删改查控制器
 */
@RestController
@RequestMapping(value = "/typeInfo")
public class TypeInfoController {

    @Resource
    private TypeInfoService typeInfoService;

    /**
     * 分页查询商品类别
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param name     商品类别名称
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<TypeInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
        return Result.success(typeInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 新增商品类别
     */
    @PostMapping
    public Result<TypeInfo> add(@RequestBody TypeInfo typeInfo){
        typeInfoService.add(typeInfo);
        return Result.success(typeInfo);
    }

    /**
     * 更新商品类别
     */
    @PutMapping
    public Result<TypeInfo> update(@RequestBody TypeInfo typeInfo){
        typeInfoService.update(typeInfo);
        return Result.success();
    }

    /**
     * 删除商品类别
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        typeInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条商品类别
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(typeInfoService.findById(id));
    }
}

