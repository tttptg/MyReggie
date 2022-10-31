package com.ymc.reggie.dto;

import com.ymc.reggie.entity.OrderDetail;
import com.ymc.reggie.entity.Orders;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
