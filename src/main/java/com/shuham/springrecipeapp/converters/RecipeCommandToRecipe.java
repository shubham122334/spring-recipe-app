package com.shuham.springrecipeapp.converters;

import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.domain.Recipe;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final NotesCommandToNotes notes;
    private final IngredientCommandToIngredient ingredient;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, NotesCommandToNotes notes,
                                 IngredientCommandToIngredient ingredient) {
        this.categoryConverter = categoryConverter;
        this.notes = notes;
        this.ingredient = ingredient;
    }

    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirection());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setImage(source.getImage());
        recipe.setNotes(notes.convert(source.getNotes()));

        if (source.getCategory() != null && source.getCategory().size() > 0){
            source.getCategory()
                    .forEach( category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredients -> recipe.getIngredients().add(ingredient.convert(ingredients)));
        }

        return recipe;
    }
}
