package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsById(Long id);

    @Query("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.author WHERE c.recipeId=?1")
    List<Comment> getCommentsByRecipeId(Long recipeId);
}
