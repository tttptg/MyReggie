package com.ymc.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymc.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
