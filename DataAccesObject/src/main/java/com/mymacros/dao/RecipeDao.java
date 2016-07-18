package com.mymacros.dao;

import com.mymacros.dto.entity.RecipeDto;

import java.util.List;

/**
 * Created by Tomas on 17/07/2016.
 */
public interface RecipeDao
{
     List<RecipeDto> getAllRecipe();
     RecipeDto getRecipeDto(long id);
     void createRecipe(RecipeDto recipeDto);
     void deleteRecipe(long id);
}
