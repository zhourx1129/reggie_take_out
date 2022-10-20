package com.zhourx.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhourx.reggie.dto.DishDto;
import com.zhourx.reggie.entity.DishFlavor;
import com.zhourx.reggie.mapper.DishFlavorMapper;
import com.zhourx.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {


}