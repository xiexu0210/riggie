package com.xiexu.reggie.dto;


import com.xiexu.reggie.entity.Setmeal;
import com.xiexu.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
