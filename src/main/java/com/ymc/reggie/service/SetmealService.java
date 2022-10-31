package com.ymc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymc.reggie.dto.SetmealDto;
import com.ymc.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void updateStatusByIds(int status,List<Long> ids);

    public void deleteWithDish(List<Long> ids);
}
