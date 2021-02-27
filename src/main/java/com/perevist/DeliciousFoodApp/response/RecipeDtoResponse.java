package com.perevist.DeliciousFoodApp.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecipeDtoResponse {
    private Long id;
    private String author;
    private String title;
    private String categoryName;
    private String imageUrl;
    private String content;
    private LocalDateTime createdDate;
}
