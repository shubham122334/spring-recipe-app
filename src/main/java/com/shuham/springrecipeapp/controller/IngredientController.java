package com.shuham.springrecipeapp.controller;

import com.shuham.springrecipeapp.commands.IngredientCommand;
import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.commands.UnitOfMeasureCommand;
import com.shuham.springrecipeapp.services.IngredientService;
import com.shuham.springrecipeapp.services.RecipeService;
import com.shuham.springrecipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){

        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));

        return "views/recipe/ingredients/list";

    }

    @PostMapping("/{recipeId}/ingredients")
    public String saveIngredients(@ModelAttribute IngredientCommand command){

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";

    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId,Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient",ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList",unitOfMeasureService.listAlluoms());
        return "views/recipe/ingredients/ingredientform";
    }

    @RequestMapping("/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId ,@PathVariable String id, Model model){

        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        return "views/recipe/ingredients/show";
    }

    @RequestMapping("/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String recipeId,@PathVariable String id,Model model){

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList",unitOfMeasureService.listAlluoms());

        return "views/recipe/ingredients/ingredientform";
    }
    @RequestMapping("/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId ,@PathVariable String id){

        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(id));
        return "redirect:/recipe/" +recipeId+ "/ingredients";
    }


}
