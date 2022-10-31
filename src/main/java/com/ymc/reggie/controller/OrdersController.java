package com.ymc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymc.reggie.common.BaseContext;
import com.ymc.reggie.common.R;
import com.ymc.reggie.dto.OrdersDto;
import com.ymc.reggie.entity.AddressBook;
import com.ymc.reggie.entity.OrderDetail;
import com.ymc.reggie.entity.Orders;
import com.ymc.reggie.entity.User;
import com.ymc.reggie.service.AddressBookService;
import com.ymc.reggie.service.OrderDetailService;
import com.ymc.reggie.service.OrdersService;
import com.ymc.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;

    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, Date beginTime, Date endTime){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null,Orders::getNumber,number);
        queryWrapper.between(beginTime != null && endTime != null,Orders::getOrderTime,beginTime,endTime);
        queryWrapper.orderByDesc(Orders::getOrderTime);

        ordersService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> list = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item,ordersDto);
            User user = userService.getById(item.getUserId());
            ordersDto.setUserName(user.getName());
            AddressBook addressBook = addressBookService.getById(item.getAddressBookId());
            ordersDto.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                    + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                    + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                    + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);

        return R.success(ordersDtoPage);
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize){
        Page<Orders> ordersPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(ordersPage, queryWrapper);

        Page<OrdersDto> ordersDtoPage = new Page<>();
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");

        List<Orders> records = ordersPage.getRecords();
        List<OrdersDto> list = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item,ordersDto);
            Long orderId = item.getId();

            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderId,orderId);

            List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailLambdaQueryWrapper);
            ordersDto.setOrderDetails(orderDetailList);
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);

        return R.success(ordersDtoPage);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    @PutMapping
    public R<String> changeStatus(@RequestBody Orders orders){

        ordersService.updateById(orders);

        return R.success("状态修改成功");
    }

    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders){

        ordersService.again(orders);

        return R.success("下单成功");
    }
}
