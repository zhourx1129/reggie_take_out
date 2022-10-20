package com.zhourx.reggie.dto;


import com.zhourx.reggie.entity.Setmeal;
import com.zhourx.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
