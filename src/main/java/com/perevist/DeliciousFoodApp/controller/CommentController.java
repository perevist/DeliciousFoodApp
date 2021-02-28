package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.request.CommentDtoRequest;
import com.perevist.DeliciousFoodApp.response.CommentDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{recipeId}/comments")
    public List<CommentDtoResponse> getRecipeComments(@PathVariable Long recipeId) {
        return commentService.getRecipeComments(recipeId);
    }

    @PostMapping("/{recipeId}/comments")
    public CommentDtoResponse addComment(@PathVariable Long recipeId, @RequestBody @Valid CommentDtoRequest commentDtoRequest) {
        return commentService.addComment(recipeId, commentDtoRequest);
    }

    @DeleteMapping("/{recipeId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long recipeId, @PathVariable Long commentId) {
        commentService.deleteComment(recipeId, commentId);
    }

    @PutMapping("/{recipeId}/comments/{commentId}")
    public CommentDtoResponse putComment(@PathVariable Long recipeId, @PathVariable Long commentId,
                                         @RequestBody @Valid CommentDtoRequest commentDtoRequest) {
        return commentService.putComment(recipeId, commentId, commentDtoRequest);
    }
}
