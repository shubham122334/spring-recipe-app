package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe>  getRecipes();
    Recipe findById(Long id);

    RecipeCommand findCommandById(Long l);

    void deleteById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
