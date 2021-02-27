package com.perevist.DeliciousFoodApp.service;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import com.perevist.DeliciousFoodApp.mapper.RecipeMapper;
import com.perevist.DeliciousFoodApp.model.Recipe;
import com.perevist.DeliciousFoodApp.model.RecipeCategory;
import com.perevist.DeliciousFoodApp.model.User;
import com.perevist.DeliciousFoodApp.repository.RecipeCategoryRepository;
import com.perevist.DeliciousFoodApp.repository.RecipeRepository;
import com.perevist.DeliciousFoodApp.repository.UserRepository;
import com.perevist.DeliciousFoodApp.request.RecipeDtoRequest;
import com.perevist.DeliciousFoodApp.response.RecipeDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.perevist.DeliciousFoodApp.model.Authority.AuthorityName.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final UserRepository userRepository;
    private final static int PAGE_SIZE = 3;

    @Override
    public List<RecipeDtoResponse> getRecipes(int page) {
        return recipeRepository.findAllRecipes(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("createdDate"))))
                .stream()
                .map(RecipeMapper::mapRecipeToRecipeDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecipeDtoResponse addRecipe(RecipeDtoRequest recipeDtoRequest) {
        Recipe recipe = createRecipeFromRecipeDtoRequest(recipeDtoRequest);
        String loggedUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userRepository.findByUsername(loggedUser)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.USER_DOES_NOT_EXIST));
        recipe.setAuthor(author);
        return RecipeMapper.mapRecipeToRecipeDtoResponse(recipeRepository.save(recipe));
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        Recipe recipe = getRecipeFromDb(id);
        verifyIfUserCanDoOperation(recipe);
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeDtoResponse putRecipe(Long id, RecipeDtoRequest recipeDtoRequest) {
        Recipe recipe = getRecipeFromDb(id);
        verifyIfUserCanDoOperation(recipe);
        recipe.setRecipeCategory(getRecipeCategoryFromDb(recipeDtoRequest.getCategoryId()));
        recipe.setTitle(recipeDtoRequest.getTitle());
        recipe.setContent(recipeDtoRequest.getContent());
        recipe.setImageUrl(recipeDtoRequest.getImageUrl());
        return RecipeMapper.mapRecipeToRecipeDtoResponse(recipe);
    }

    @Override
    @Transactional
    public RecipeDtoResponse patchRecipe(Long id, RecipeDtoRequest recipeDtoRequest) {
        Recipe recipe = getRecipeFromDb(id);
        verifyIfUserCanDoOperation(recipe);
        if (recipeDtoRequest.getCategoryId() != null) {
            recipe.setRecipeCategory(getRecipeCategoryFromDb(recipeDtoRequest.getCategoryId()));
        }
        if (recipeDtoRequest.getContent() != null) {
            recipe.setContent(recipeDtoRequest.getContent());
        }
        if (recipeDtoRequest.getTitle() != null) {
            recipe.setTitle(recipeDtoRequest.getTitle());
        }
        if (recipeDtoRequest.getImageUrl() != null) {
            recipe.setImageUrl(recipeDtoRequest.getImageUrl());
        }
        return RecipeMapper.mapRecipeToRecipeDtoResponse(recipe);
    }

    // User can perform operations on its own recipes only
    // Admin can perform operations on all recipes
    private void verifyIfUserCanDoOperation(Recipe recipe) {
        if (!verifyIfUserHasRoleAdmin()) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String author = recipe.getAuthor().getUsername();
            if (!username.equals(author)) {
                throw new DeliciousFoodAppException(Error.USER_CAN_NOT_DO_THIS_OPERATION);
            }
        }
    }

    private boolean verifyIfUserHasRoleAdmin() {
        Set<GrantedAuthority> authorities = new HashSet<>(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        );
        return authorities.stream()
                .anyMatch(auth -> auth.toString().equals(ROLE_ADMIN.name()));
    }

    private Recipe createRecipeFromRecipeDtoRequest(RecipeDtoRequest recipeDtoRequest) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDtoRequest.getTitle());
        recipe.setImageUrl(recipeDtoRequest.getImageUrl());
        recipe.setContent(recipeDtoRequest.getContent());
        RecipeCategory recipeCategory = getRecipeCategoryFromDb(recipeDtoRequest.getCategoryId());
        recipe.setRecipeCategory(recipeCategory);
        recipe.setCreatedDate(LocalDateTime.now());
        return recipe;
    }

    private Recipe getRecipeFromDb(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.RECIPE_DOES_NOT_EXIST));
    }

    private RecipeCategory getRecipeCategoryFromDb(Long id) {
        return recipeCategoryRepository.findById(id)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.CATEGORY_DOES_NOT_EXIST));
    }
}
