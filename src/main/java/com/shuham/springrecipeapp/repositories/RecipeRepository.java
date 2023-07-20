package com.shuham.springrecipeapp.repositories;

import com.shuham.springrecipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
