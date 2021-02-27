package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Eliminate N+1 problem
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.author LEFT JOIN FETCH r.recipeCategory")
    List<Recipe> findAllRecipes(Pageable pageable);

    boolean existsById(Long id);
}
