package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe>  getRecipes();

    Recipe findById(Long id);
}
