package com.ymc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymc.reggie.common.R;
import com.ymc.reggie.dto.DishDto;
import com.ymc.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getDishDtoById(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public R<String> deleteWithFlavor(List<Long> ids);

}
