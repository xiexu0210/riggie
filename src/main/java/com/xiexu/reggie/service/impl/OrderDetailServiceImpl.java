package com.xiexu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiexu.reggie.entity.OrderDetail;
import com.xiexu.reggie.mapper.OrdersDetailMapper;
import com.xiexu.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrderDetailService {

}
