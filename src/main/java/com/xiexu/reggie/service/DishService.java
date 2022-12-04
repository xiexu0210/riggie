package com.xiexu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiexu.reggie.dto.DishDto;
import com.xiexu.reggie.entity.Dish;

public interface DishService  extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByidWithFlavor(Long id);

    public void updateFlavor(DishDto dishDto);
}
