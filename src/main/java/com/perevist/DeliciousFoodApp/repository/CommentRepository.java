package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
