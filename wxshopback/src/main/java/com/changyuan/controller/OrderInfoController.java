package com.changyuan.controller;

import com.changyuan.common.Common;
import com.changyuan.common.Result;
import com.changyuan.common.ResultCode;
import com.changyuan.entity.GoodsInfo;
import com.changyuan.entity.OrderInfo;
import com.changyuan.entity.UserInfo;
import com.changyuan.exception.CustomException;
import com.changyuan.service.OrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单控制器
 */
@RestController
@RequestMapping(value = "orderInfo")
public class    OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 下单
     */
    @PostMapping
    public Result<OrderInfo> add(@RequestBody OrderInfo orderInfo){
        Long userId = orderInfo.getUserid();
        List<GoodsInfo> goodsList = orderInfo.getGoodsList();
        if(userId==null||goodsList==null||goodsList.size()==0){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        orderInfo.setState("待付款");
        return Result.success(orderInfoService.add(orderInfo));
    }

    /**
     * 查询所有信息(分页)
     */
    @GetMapping("/page")
    public Result<PageInfo<OrderInfo>> findPage(@RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                     @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                                     HttpServletRequest request){
        return Result.success(orderInfoService.findPages(userId,pageNum,pageSize,request));
    }

    /**
     * 查询所有信息(分页)
     */
    @GetMapping("/page/front")
    public Result<PageInfo<OrderInfo>> findFrontPage(@RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false)String state,
                                                     @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                     @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        return Result.success(orderInfoService.findFrontPages(userId,state,pageNum,pageSize));
    }

    /**
     * 修改订单状态
     */
    @PostMapping("/state/{id}/{state}")
    public Result state(@PathVariable Long id,@PathVariable String state){
        orderInfoService.changeState(id,state);
        return Result.success();
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据订单id查询所有商品
     */
    @GetMapping("/order/{id}")
    public Result<OrderInfo> findById(@PathVariable Long id) {
        OrderInfo orderInfo = orderInfoService.findById(id);
        return Result.success(orderInfo);
    }

}
