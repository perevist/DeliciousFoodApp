package com.perevist.DeliciousFoodApp.service.interfaces;

import com.perevist.DeliciousFoodApp.model.RecipeCategory;
import com.perevist.DeliciousFoodApp.request.RecipeCategoryDtoRequest;

import java.util.List;

public interface RecipeCategoryService {
    List<RecipeCategory> getRecipeCategories();

    RecipeCategory addRecipeCategory(RecipeCategoryDtoRequest recipeCategoryDtoRequest);

    void deleteRecipeCategory(Long id);

    RecipeCategory putRecipeCategory(Long id, RecipeCategoryDtoRequest recipeCategoryDtoRequest);
}
