package com.zhourx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhourx.reggie.entity.ShoppingCart;
import com.zhourx.reggie.mapper.ShoppingCartMapper;
import com.zhourx.reggie.service.ShoppingCartService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
