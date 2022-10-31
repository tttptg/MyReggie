package com.ymc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymc.reggie.common.CustomException;
import com.ymc.reggie.entity.Category;
import com.ymc.reggie.entity.Dish;
import com.ymc.reggie.entity.Setmeal;
import com.ymc.reggie.mapper.CategoryMapper;
import com.ymc.reggie.service.CategoryService;
import com.ymc.reggie.service.DishService;
import com.ymc.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);

        if (dishCount > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);

        if (setmealCount > 0){
            throw  new CustomException("当前分类下关联了套餐，不能删除");
        }

        super.removeById(id);

    }
}
