package com.xiexu.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiexu.reggie.common.R;
import com.xiexu.reggie.dto.DishDto;
import com.xiexu.reggie.entity.Category;
import com.xiexu.reggie.entity.Dish;
import com.xiexu.reggie.entity.DishFlavor;
import com.xiexu.reggie.service.CategoryService;
import com.xiexu.reggie.service.DishFlavorService;
import com.xiexu.reggie.service.DishService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    private R<String> save(@RequestBody DishDto  dishDto){
        dishService.saveWithFlavor(dishDto);

        return  R.success("Ass");
    }

    //菜品分类查询思想
    //回传的数据在两个表内、
    //因此需要先分页查询一个表中的字段，提取该分页返回的数值
    //然后拷贝该对象的数值，替换records
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageinfo = new Page<>(page, pageSize);

        Page<DishDto> dishDtoPage = new Page<>();


        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(!StringUtils.isEmpty(name), Dish::getName, name );
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageinfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageinfo, dishDtoPage, "records");

        List<Dish> records = pageinfo.getRecords();
        List<DishDto> list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            //根据id查分类对象
            Category category =  categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);


    }


    //restful风格加上@RequestParam（省略不写）
    @PostMapping("/status/{status}")
    public R<String> udpateStatus(@PathVariable int status, Long[] ids){
//        System.out.println(status);
//        System.out.println(ids);
        Dish dish = new Dish();
        for (Long id : ids){
            dish.setStatus(status);
            dish.setId(id);
            dishService.updateById(dish);

        }

        return  R.success("状态更改成功");
    }



    @GetMapping("/{id}")
    public R<DishDto> getByid(@PathVariable Long id){
        DishDto dishDto = dishService.getByidWithFlavor(id);
        return  R.success(dishDto);
    }

    @PutMapping
    public R<String> udpate(@RequestBody DishDto  dishDto){

        dishService.updateFlavor(dishDto);
        return R.success("更改成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        Dish dish = new Dish();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        for (Long id : ids){
//            lambdaQueryWrapper.eq(Dish::getCategoryId,id);
            dishService.removeById(id);
            dishFlavorService.removeById(id);

        }

        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R< List <DishDto>> list(Long categoryId){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, categoryId);

        //条件排序条件
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishList  = dishService.list(dishLambdaQueryWrapper);

        List<DishDto> dishDtoList = dishList.stream().map(item ->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            //根据id查分类对象
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL: select* from dishflavor where dish_id=?;
            List<DishFlavor> dishFlavorlist = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorlist);
            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }



}
