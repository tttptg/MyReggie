package com.ymc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymc.reggie.entity.OrderDetail;
import com.ymc.reggie.mapper.OrderDetailMapper;
import com.ymc.reggie.service.OrderDetailService;
import com.ymc.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
