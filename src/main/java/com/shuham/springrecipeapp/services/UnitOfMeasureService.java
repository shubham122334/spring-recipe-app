package com.shuham.springrecipeapp.services;

import com.shuham.springrecipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAlluoms();
}
