package com.shuham.springrecipeapp.converters;

import com.shuham.springrecipeapp.commands.IngredientCommand;
import com.shuham.springrecipeapp.domain.Ingredient;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand) {
        this.unitOfMeasureCommand = unitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if(source==null)
         return null;

        final IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand.convert(source.getUnitOfMeasure()));

        return ingredientCommand;
    }
}
