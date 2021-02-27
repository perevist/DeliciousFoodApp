package com.perevist.DeliciousFoodApp.service.interfaces;

import com.perevist.DeliciousFoodApp.request.RecipeDtoRequest;
import com.perevist.DeliciousFoodApp.response.RecipeDtoResponse;

import java.util.List;

public interface RecipeService {
    List<RecipeDtoResponse> getRecipes(int page);

    RecipeDtoResponse addRecipe(RecipeDtoRequest recipeDtoRequest);

    void deleteRecipe(Long id);

    RecipeDtoResponse putRecipe(Long id, RecipeDtoRequest recipeDtoRequest);

    RecipeDtoResponse patchRecipe(Long id, RecipeDtoRequest recipeDtoRequest);
}
