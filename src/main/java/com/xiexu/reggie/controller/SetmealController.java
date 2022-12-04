package com.xiexu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiexu.reggie.common.R;
import com.xiexu.reggie.dto.SetmealDto;
import com.xiexu.reggie.entity.Category;
import com.xiexu.reggie.entity.Setmeal;
import com.xiexu.reggie.service.CategoryService;
import com.xiexu.reggie.service.SetmealDishService;
import com.xiexu.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("setmeal:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page>  page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();


        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null, Setmeal::getName, name);

        setmealService.page(setmealPage, setmealLambdaQueryWrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<Setmeal> setmealList = setmealPage.getRecords();

        List<SetmealDto> setmealDtoList  =setmealList.stream().map(item ->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);


            Category c = categoryService.getById(item.getCategoryId());
            if(c != null){
                setmealDto.setCategoryName(c.getName());
            }
            return  setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoList);

        return  R.success(setmealDtoPage) ;


    }


    @DeleteMapping
    public R<String> detete(Long[] ids ){

        setmealService.deleteWithDish(ids);


        return  R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> statuskill(@PathVariable int status, Long[] ids){
//        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        setmealLambdaQueryWrapper.
        for (Long id :ids){
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(status);
            setmeal.setId(id);
            setmealService.updateById(setmeal);
        }


        return R.success("状态更改成功");
    }

    @GetMapping("/list")
    public R< List<Setmeal> > getlist(Setmeal setmeal ){
//        Category category = new Category();
        LambdaQueryWrapper<Setmeal> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();

        categoryLambdaQueryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        categoryLambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId() );
        categoryLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(categoryLambdaQueryWrapper);

        return R.success(setmealList);
    }


}
