package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(Long recipeId ,Long id);
}
