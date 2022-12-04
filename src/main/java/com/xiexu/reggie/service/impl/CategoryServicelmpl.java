package com.xiexu.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiexu.reggie.common.CustomException;
import com.xiexu.reggie.entity.Category;
import com.xiexu.reggie.entity.Dish;
import com.xiexu.reggie.entity.Setmeal;
import com.xiexu.reggie.mapper.CategoryMapper;
import com.xiexu.reggie.service.CategoryService;
import com.xiexu.reggie.service.DishService;
import com.xiexu.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicelmpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        if (count1 > 0){
            throw new CustomException("已经关联菜品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id );
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if(count2 > 0){
            throw  new CustomException("已经关联套餐不能删除");
        }
        super.removeById(id);
    }

}
