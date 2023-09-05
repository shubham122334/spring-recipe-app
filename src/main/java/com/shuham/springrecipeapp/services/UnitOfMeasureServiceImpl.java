package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.UnitOfMeasureCommand;
import com.shuham.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.shuham.springrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureCommand = unitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAlluoms() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(),false)
                .map(unitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
