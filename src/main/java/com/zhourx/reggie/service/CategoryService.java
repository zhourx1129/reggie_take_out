package com.zhourx.reggie.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhourx.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
