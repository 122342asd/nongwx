package com.changyuan.controller;

import com.changyuan.common.Result;
import com.changyuan.entity.UserInfo;
import com.changyuan.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户增删改查控制器
 */
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询用户
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param name     用户名
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<UserInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
        return Result.success(userInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<UserInfo> add(@RequestBody UserInfo userInfo){
        userInfoService.add(userInfo);
        return Result.success(userInfo);
    }

    /**
     * 更新用户
     */
    @PutMapping
    public Result<UserInfo> update(@RequestBody UserInfo userInfo){
        userInfoService.update(userInfo);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        userInfoService.delete(id);
        return Result.success();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public Result<UserInfo> detail(@PathVariable Long id){
        UserInfo userInfo = userInfoService.findById(id);
        return Result.success(userInfo);
    }
}

