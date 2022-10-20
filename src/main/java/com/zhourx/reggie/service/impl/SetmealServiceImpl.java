package com.zhourx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhourx.reggie.common.CustomException;
import com.zhourx.reggie.dto.SetmealDto;
import com.zhourx.reggie.entity.Setmeal;

import com.zhourx.reggie.entity.SetmealDish;
import com.zhourx.reggie.mapper.SetmealMapper;
import com.zhourx.reggie.service.SetmealDishService;
import com.zhourx.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        // 保存套餐和菜品的关联信息，操作setmeal_dish表，执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        long count = this.count(queryWrapper);
        if (count > 0) {
            // 如果不能删除，则抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        // 如果可以删除，先删除套餐中的数据---setmeal
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetmealDish::getId,ids);
        // 删除关系表中的数据，setmeal_dish
        setmealDishService.remove(wrapper);
    }
}
