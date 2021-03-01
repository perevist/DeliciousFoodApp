package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.model.RecipeCategory;
import com.perevist.DeliciousFoodApp.request.RecipeCategoryDtoRequest;
import com.perevist.DeliciousFoodApp.service.interfaces.RecipeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe-categories")
@RequiredArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService recipeCategoryService;

    @GetMapping
    public List<RecipeCategory> getRecipeCategories() {
        return recipeCategoryService.getRecipeCategories();
    }

    @PostMapping
    public RecipeCategory addRecipeCategory(@RequestBody @Valid RecipeCategoryDtoRequest recipeCategoryDtoRequest) {
        return recipeCategoryService.addRecipeCategory(recipeCategoryDtoRequest);
    }

    @PutMapping("/{id}")
    public RecipeCategory putRecipeCategory(@PathVariable Long id,
                                            @RequestBody @Valid RecipeCategoryDtoRequest recipeCategoryDtoRequest) {

        return recipeCategoryService.putRecipeCategory(id, recipeCategoryDtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeCategory(@PathVariable Long id) {
        recipeCategoryService.deleteRecipeCategory(id);
    }
}
