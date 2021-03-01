package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsById(Long id);

    @Query("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.author WHERE c.recipeId=?1")
    List<Comment> getCommentsByRecipeId(Long recipeId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.recipeId=?1")
    void deleteAllByRecipeId(Long recipeId);
}
