package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.request.RecipeDtoRequest;
import com.perevist.DeliciousFoodApp.response.RecipeDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeDtoResponse> getRecipes(@RequestParam(required = false) Integer page) {
        int pageNumber = (page != null && page >= 1) ? page - 1 : 0;
        return recipeService.getRecipes(pageNumber);
    }

    @PostMapping
    public RecipeDtoResponse addRecipe(@RequestBody @Valid RecipeDtoRequest recipeDtoRequest) {
        return recipeService.addRecipe(recipeDtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/{id}")
    public RecipeDtoResponse putRecipe(@PathVariable Long id,
                                       @RequestBody @Valid RecipeDtoRequest recipeDtoRequest) {
        return recipeService.putRecipe(id, recipeDtoRequest);
    }

    @PatchMapping("/{id}")
    public RecipeDtoResponse patchRecipe(@PathVariable Long id,
                                         @RequestBody RecipeDtoRequest recipeDtoRequest) {
        return recipeService.patchRecipe(id, recipeDtoRequest);
    }
}
