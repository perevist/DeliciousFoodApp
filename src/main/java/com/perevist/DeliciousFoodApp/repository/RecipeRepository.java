package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
