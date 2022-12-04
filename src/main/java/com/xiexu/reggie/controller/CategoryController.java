package com.xiexu.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiexu.reggie.common.R;
import com.xiexu.reggie.entity.Category;
import com.xiexu.reggie.entity.Employee;
import com.xiexu.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {



    @Autowired
    private CategoryService categoryService;

    @PostMapping
    private R<String> save(@RequestBody Category category ){

        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }



    //类别信息分类查询信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);


        categoryService.page(pageInfo, queryWrapper);;

        return  R.success(pageInfo);
    }


    //根据id删除分类
    @DeleteMapping
    public R<String> detele(Long ids){
        log.info("删除分类，id为{}",ids);
//        categoryService.removeById(id);
        categoryService.remove(ids);
        return  R.success("分类信息删除成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category ){
        categoryService.updateById(category);
        return  R.success("分类修改成功");
    }


    @GetMapping("/list")//查询菜品的分类在 list列表展示
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);
        List<Category> categoryList =categoryService.list(lambdaQueryWrapper);
        return  R.success(categoryList);
    }




}
