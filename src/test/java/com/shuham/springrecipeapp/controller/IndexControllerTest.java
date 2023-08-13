package com.shuham.springrecipeapp.controller;

import com.shuham.springrecipeapp.domain.Recipe;
import com.shuham.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    IndexController controller;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller=new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        Set<Recipe> recipes=new HashSet<>();

        Recipe r=new Recipe();
        r.setId(1L);
        recipes.add(r);

        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor=ArgumentCaptor.forClass(Set.class);

        //when
        String viewPage= controller.getIndexPage(model);

        //then
        assertEquals("views/index",viewPage);
        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());

        Set<Recipe> setInController=argumentCaptor.getValue();
        assertEquals(2,setInController.size());
    }
}