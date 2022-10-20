package com.zhourx.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhourx.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
