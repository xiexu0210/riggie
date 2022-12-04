package com.xiexu.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiexu.reggie.dto.SetmealDto;
import com.xiexu.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void deleteWithDish(Long[] inds);
}
