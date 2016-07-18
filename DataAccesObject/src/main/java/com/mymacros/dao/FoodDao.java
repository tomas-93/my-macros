package com.mymacros.dao;

import com.mymacros.dto.entity.FoodDto;

import java.util.List;

/**
 * Created by Tomas on 17/07/2016.
 */
public interface FoodDao
{
     List<FoodDto> getAllFood();
     FoodDto getFood(long id);
     void createFood(FoodDto foodDto);
     void deleteFood(long id);
}
