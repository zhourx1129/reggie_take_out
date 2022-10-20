package com.zhourx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhourx.reggie.dto.DishDto;
import com.zhourx.reggie.entity.Dish;
import org.springframework.stereotype.Service;

public interface DishService extends IService<Dish> {
    // 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor

    public void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id来查询对应的菜品信息和口味信息
     * @param id
     */
    public DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息，同时更新对应的口味信息
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto);
}
