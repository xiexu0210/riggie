package com.xiexu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiexu.reggie.entity.Employee;


//与BaseMapper类似，MP也封装了业务层的各种方法。简化了开发过程：
public interface EmployeeService extends IService<Employee> {
}