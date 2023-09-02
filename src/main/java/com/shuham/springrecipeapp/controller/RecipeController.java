package com.shuham.springrecipeapp.controller;

import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String showById(@PathVariable  Long id , Model model){

        model.addAttribute("recipe",recipeService.findById(id));
        return "views/recipe/show";
    }

    @RequestMapping("/{id}/update")
    public String update(@PathVariable Long id , Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "views/recipe/recipe-form";
    }

    @RequestMapping("/new/")
    public String newRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());

        return "views/recipe/recipe-form";
    }

    @PostMapping("/recipe/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId()+ "/show";
    }
}
