package com.perevist.DeliciousFoodApp.mapper;

import com.perevist.DeliciousFoodApp.model.Recipe;
import com.perevist.DeliciousFoodApp.response.RecipeDtoResponse;

public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDtoResponse mapRecipeToRecipeDtoResponse(Recipe recipe) {
        return RecipeDtoResponse.builder()
                .id(recipe.getId())
                .author(recipe.getAuthor().getUsername())
                .title(recipe.getTitle())
                .categoryName(recipe.getRecipeCategory().getName())
                .imageUrl(recipe.getImageUrl())
                .content(recipe.getContent())
                .createdDate(recipe.getCreatedDate())
                .build();
    }
}
