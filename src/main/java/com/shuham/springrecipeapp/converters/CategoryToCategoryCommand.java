package com.shuham.springrecipeapp.converters;

import com.shuham.springrecipeapp.commands.CategoryCommand;
import com.shuham.springrecipeapp.domain.Category;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if(source==null)
        return null;

        final CategoryCommand categoryCommand=new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());
        return categoryCommand;
    }
}
