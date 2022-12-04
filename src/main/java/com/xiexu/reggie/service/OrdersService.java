package com.xiexu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiexu.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
