package com.shuham.springrecipeapp.repositories;


import com.shuham.springrecipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {


    Optional<UnitOfMeasure> findByDescription(String description);


}