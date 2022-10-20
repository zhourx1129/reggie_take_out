package com.zhourx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhourx.reggie.common.CustomException;
import com.zhourx.reggie.entity.Category;
import com.zhourx.reggie.entity.Dish;
import com.zhourx.reggie.entity.Setmeal;
import com.zhourx.reggie.mapper.CategoryMapper;
import com.zhourx.reggie.service.CategoryService;
import com.zhourx.reggie.service.DishService;

import com.zhourx.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setMealService;
    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        queryWrapper.eq(Dish::getCategoryId,id);
        long count = dishService.count(queryWrapper);
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count > 0) {
            // 已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        lambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        long count1 = setMealService.count(lambdaQueryWrapper);
        if (count1>0){
            // 已经关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        // 正常删除分类
        super.removeById(id);
    }
}
