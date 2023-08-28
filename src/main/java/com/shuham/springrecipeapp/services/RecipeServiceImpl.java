package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.converters.RecipeCommandToRecipe;
import com.shuham.springrecipeapp.converters.RecipeToRecipeCommand;
import com.shuham.springrecipeapp.domain.Recipe;
import com.shuham.springrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;


    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }


    @Override
    public Set<Recipe> getRecipes() {
        log.debug("i am the service");
        Set<Recipe> recipes=new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe=recipeRepository.findById(id);
        if(recipe.isEmpty())
            throw new RuntimeException("not found any recipe with id :"+id);

        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe=recipeCommandToRecipe.convert(command);


        Recipe saveRecipe=recipeRepository.save(recipe);
        log.debug("Saved RecipeId:" + saveRecipe.getId());
        return recipeToRecipeCommand.convert(saveRecipe);
    }
}
