package com.perevist.DeliciousFoodApp.service.interfaces;

import com.perevist.DeliciousFoodApp.request.CommentDtoRequest;
import com.perevist.DeliciousFoodApp.response.CommentDtoResponse;

import java.util.List;

public interface CommentService {
    List<CommentDtoResponse> getRecipeComments(Long recipeId);

    CommentDtoResponse addComment(Long recipeId, CommentDtoRequest commentDtoRequest);

    void deleteComment(Long recipeId, Long commentId);

    CommentDtoResponse putComment(Long recipeId, Long commentId, CommentDtoRequest commentDtoRequest);
}
