package com.changyuan.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.changyuan.common.Common;
import com.changyuan.common.Result;
import com.changyuan.common.ResultCode;
import com.changyuan.entity.UserInfo;
import com.changyuan.exception.CustomException;
import com.changyuan.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 *登录、退出相关的控制器
 */
@RestController
public class AccountController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        //todo 从数据库查询账号密码是否正确,放到session
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,login);
        session.setMaxInactiveInterval(60*60*24);
        return Result.success(login);
    }

    /**
     * 重置密码为123456
     */
    @PostMapping("/resetPassword")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo){
        return Result.success(userInfoService.resetPassword(userInfo.getName()));
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().setAttribute(Common.USER_INFO,null);
        return Result.success();
    }

    /**
     * 小程序端用户注册
     */
    @PostMapping("/register")
    public Result<UserInfo> register(@RequestBody UserInfo userInfo,HttpServletRequest request){
        if(StrUtil.isBlank(userInfo.getName()) ||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        UserInfo register = userInfoService.add(userInfo);
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,register);
        session.setMaxInactiveInterval(60*60*24);
        return Result.success(register);
    }

    /**
     * 判断用户是否已登陆
     */
    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request){
        Object user = request.getSession().getAttribute(Common.USER_INFO);
        if(user == null){
            return Result.error("401","未登录");
        }
        return Result.success(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserInfo userInfo,HttpServletRequest request){
        Object user1 = request.getSession().getAttribute(Common.USER_INFO);
        if(user1 == null){
            return Result.error("401","未登录");
        }
        UserInfo user = (UserInfo)user1;
        String oldPassword = SecureUtil.md5(userInfo.getPassword());
        if(!oldPassword.equals(user.getPassword())){
            return Result.error(ResultCode.USER_ACCOUNT_ERROR.code,ResultCode.USER_ACCOUNT_ERROR.msg);
        }
        user.setPassword(SecureUtil.md5(userInfo.getNewPassword()));
        userInfoService.update(user);
        //清空session·让用户重新登陆
        request.getSession().setAttribute(Common.USER_INFO,null);
        return Result.success();
    }
}
