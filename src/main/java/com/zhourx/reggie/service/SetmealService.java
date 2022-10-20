package com.zhourx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhourx.reggie.dto.SetmealDto;
import com.zhourx.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除菜品关联的数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
