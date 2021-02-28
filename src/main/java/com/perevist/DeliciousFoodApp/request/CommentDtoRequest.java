package com.perevist.DeliciousFoodApp.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentDtoRequest {
    @NotBlank
    private String content;
}
