package com.perevist.DeliciousFoodApp.mapper;

import com.perevist.DeliciousFoodApp.model.Comment;
import com.perevist.DeliciousFoodApp.response.CommentDtoResponse;

public class CommentMapper {

    private CommentMapper() {
    }

    public static CommentDtoResponse mapCommentToCommentDtoResponse(Comment comment) {
        return CommentDtoResponse.builder()
                .id(comment.getId())
                .recipeId(comment.getRecipeId())
                .author(comment.getAuthor().getUsername())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}
