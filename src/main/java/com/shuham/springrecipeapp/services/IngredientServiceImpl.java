package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.IngredientCommand;
import com.shuham.springrecipeapp.converters.IngredientCommandToIngredient;
import com.shuham.springrecipeapp.converters.IngredientToIngredientCommand;
import com.shuham.springrecipeapp.domain.Ingredient;
import com.shuham.springrecipeapp.domain.Recipe;
import com.shuham.springrecipeapp.repositories.RecipeRepository;
import com.shuham.springrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe>  recipeOptional=recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.error("recipe id not found id "+recipeId);
        }

        Recipe recipe=recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional=recipe.getIngredients().stream()
                .filter(Ingredients -> Ingredients.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){

            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

        @Override
        @Transactional
        public IngredientCommand saveIngredientCommand (IngredientCommand command){
            Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

            if (!recipeOptional.isPresent()) {

                //todo toss error if not found!
                log.error("Recipe not found for id: " + command.getRecipeId());
                return new IngredientCommand();
            } else {
                Recipe recipe = recipeOptional.get();

                Optional<Ingredient> ingredientOptional = recipe
                        .getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getId().equals(command.getId()))
                        .findFirst();

                if (ingredientOptional.isPresent()) {
                    Ingredient ingredientFound = ingredientOptional.get();
                    ingredientFound.setDescription(command.getDescription());
                    ingredientFound.setAmount(command.getAmount());
                    ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                            .findById(command.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
                } else {
                    //add new Ingredient
                    Ingredient ingredient=ingredientCommandToIngredient.convert(command);
                    ingredient.setRecipe(recipe);
                    recipe.addIngredient(ingredient);
                }

                Recipe savedRecipe = recipeRepository.save(recipe);
                Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(recipeIngredients-> recipeIngredients.getId().equals(command.getId()))
                        .findFirst();

                if(!savedIngredientOptional.isPresent()){
                    savedIngredientOptional=savedRecipe.getIngredients().stream()
                            .filter(recipeIngredients-> recipeIngredients.getDescription().equals(command.getDescription()))
                            .filter(recipeIngredients-> recipeIngredients.getAmount().equals(command.getAmount()))
                            .filter(recipeIngredients-> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                            .findFirst();
                }

                //to do check for fail
                return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            }
        }


    @Override
    public void deleteById(Long recipeId, Long id) {

        Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()) {
            log.error("recipe id not found id " + recipeId);


            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }

        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }

    }
}
