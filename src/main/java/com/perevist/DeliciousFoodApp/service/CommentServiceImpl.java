package com.perevist.DeliciousFoodApp.service;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import com.perevist.DeliciousFoodApp.mapper.CommentMapper;
import com.perevist.DeliciousFoodApp.model.Comment;
import com.perevist.DeliciousFoodApp.model.User;
import com.perevist.DeliciousFoodApp.repository.CommentRepository;
import com.perevist.DeliciousFoodApp.repository.RecipeRepository;
import com.perevist.DeliciousFoodApp.repository.UserRepository;
import com.perevist.DeliciousFoodApp.request.CommentDtoRequest;
import com.perevist.DeliciousFoodApp.response.CommentDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.perevist.DeliciousFoodApp.model.Authority.AuthorityName.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDtoResponse> getRecipeComments(Long recipeId) {
        verifyRecipeExists(recipeId);
        return commentRepository.getCommentsByRecipeId(recipeId)
                .stream()
                .map(CommentMapper::mapCommentToCommentDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDtoResponse addComment(Long recipeId, CommentDtoRequest commentDtoRequest) {
        verifyRecipeExists(recipeId);
        Comment comment = new Comment();
        User author = getCurrentlyLoggedUser();
        comment.setAuthor(author);
        comment.setContent(commentDtoRequest.getContent());
        comment.setRecipeId(recipeId);
        comment.setCreatedDate(LocalDateTime.now());
        return CommentMapper.mapCommentToCommentDtoResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long recipeId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.COMMENT_DOES_NOT_EXIST));
        verifyIfUserCanDoOperation(comment);
        verifyIfCommentBelongsToRecipe(comment, recipeId);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentDtoResponse putComment(Long recipeId, Long commentId, CommentDtoRequest commentDtoRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.COMMENT_DOES_NOT_EXIST));
        verifyIfUserCanDoOperation(comment);
        verifyIfCommentBelongsToRecipe(comment, recipeId);
        comment.setContent(commentDtoRequest.getContent());
        return CommentMapper.mapCommentToCommentDtoResponse(comment);
    }

    // User can perform operations on its own comments only
    // Admin can perform operations on all comments
    private void verifyIfUserCanDoOperation(Comment comment) {
        if (!verifyIfUserHasRoleAdmin()) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String author = comment.getAuthor().getUsername();
            if (!username.equals(author)) {
                throw new DeliciousFoodAppException(Error.USER_CAN_NOT_DO_THIS_OPERATION);
            }
        }
    }

    private boolean verifyIfUserHasRoleAdmin() {
        Set<GrantedAuthority> authorities = new HashSet<>(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        );
        return authorities.stream()
                .anyMatch(auth -> auth.toString().equals(ROLE_ADMIN.name()));
    }

    private void verifyIfCommentBelongsToRecipe(Comment comment, Long recipeId) {
        if (!comment.getRecipeId().equals(recipeId)) {
            throw new DeliciousFoodAppException(Error.COMMENT_DOES_NOT_BELONG_TO_THIS_RECIPE);
        }
    }

    private void verifyRecipeExists(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new DeliciousFoodAppException(Error.RECIPE_DOES_NOT_EXIST);
        }
    }

    private User getCurrentlyLoggedUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.USER_DOES_NOT_EXIST));
    }
}
