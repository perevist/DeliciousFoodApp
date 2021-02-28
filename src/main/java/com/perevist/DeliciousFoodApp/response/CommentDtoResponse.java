package com.perevist.DeliciousFoodApp.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDtoResponse {
    private Long id;
    private Long recipeId;
    private String author;
    private String content;
    private LocalDateTime createdDate;
}
