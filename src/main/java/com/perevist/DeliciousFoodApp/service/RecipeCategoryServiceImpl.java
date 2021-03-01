package com.perevist.DeliciousFoodApp.service;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import com.perevist.DeliciousFoodApp.model.RecipeCategory;
import com.perevist.DeliciousFoodApp.repository.RecipeCategoryRepository;
import com.perevist.DeliciousFoodApp.request.RecipeCategoryDtoRequest;
import com.perevist.DeliciousFoodApp.service.interfaces.RecipeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    private final RecipeCategoryRepository recipeCategoryRepository;

    @Override
    public List<RecipeCategory> getRecipeCategories() {
        return recipeCategoryRepository.findAll();
    }

    @Override
    @Transactional
    public RecipeCategory addRecipeCategory(RecipeCategoryDtoRequest recipeCategoryDtoRequest) {
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategory.setName(recipeCategoryDtoRequest.getName());
        try {
            return recipeCategoryRepository.save(recipeCategory);
        } catch (DataIntegrityViolationException e) {
            throw new DeliciousFoodAppException(Error.CATEGORY_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void deleteRecipeCategory(Long id) {
        try {
            recipeCategoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DeliciousFoodAppException(Error.CATEGORY_DOES_NOT_EXIST);
        }
    }

    @Override
    @Transactional
    public RecipeCategory putRecipeCategory(Long id, RecipeCategoryDtoRequest recipeCategoryDtoRequest) {
        RecipeCategory recipeCategory = recipeCategoryRepository.findById(id)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.CATEGORY_DOES_NOT_EXIST));
        recipeCategory.setName(recipeCategoryDtoRequest.getName());

        try {
            return recipeCategoryRepository.saveAndFlush(recipeCategory);
        } catch (DataIntegrityViolationException e) {
            throw new DeliciousFoodAppException(Error.CATEGORY_ALREADY_EXISTS);
        }
    }
}
