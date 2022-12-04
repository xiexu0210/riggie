package com.xiexu.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiexu.reggie.dto.DishDto;
import com.xiexu.reggie.entity.Dish;
import com.xiexu.reggie.entity.DishFlavor;
import com.xiexu.reggie.mapper.DishMapeer;
import com.xiexu.reggie.service.DishFlavorService;
import com.xiexu.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapeer, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;



    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishid = dishDto.getId();

        //对flavors属性进行过滤， 设置器DIshID属性
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishid);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    @Transactional //查询菜品及菜品对应的口味
    public DishDto getByidWithFlavor(Long id) {
        //查询菜品信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties( dish ,dishDto);

        LambdaQueryWrapper<DishFlavor> dishDtoLambdaQueryWrapper= new LambdaQueryWrapper<>();
        dishDtoLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(dishDtoLambdaQueryWrapper);
        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateFlavor(DishDto dishDto) {

        this.updateById(dishDto);
        Long dishid = dishDto.getId();

        //对flavors属性进行过滤， 设置器DIshID属性
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishid);
            dishFlavorService.removeById(item);

            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }


}
