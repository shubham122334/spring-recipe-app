package com.shuham.springrecipeapp.converters;

import com.shuham.springrecipeapp.commands.UnitOfMeasureCommand;
import com.shuham.springrecipeapp.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if(source==null)
         return null;

        final UnitOfMeasureCommand unitOfMeasureCommand=new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(source.getId());
        unitOfMeasureCommand.setDescription(source.getDescription());
        return unitOfMeasureCommand;
    }
}
