package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
}
