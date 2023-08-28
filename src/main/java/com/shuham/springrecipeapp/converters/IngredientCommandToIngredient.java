package com.shuham.springrecipeapp.converters;

import com.shuham.springrecipeapp.commands.IngredientCommand;
import com.shuham.springrecipeapp.domain.Ingredient;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source==null)
         return null;

        final Ingredient ingredient=new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(unitOfMeasure.convert(source.getUnitOfMeasure()));

        return ingredient;
    }
}
