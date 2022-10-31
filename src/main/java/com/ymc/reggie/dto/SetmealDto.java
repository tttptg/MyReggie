package com.ymc.reggie.dto;

import com.ymc.reggie.entity.Setmeal;
import com.ymc.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
