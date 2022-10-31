package com.ymc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymc.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);

    public void again(Orders orders);
}
