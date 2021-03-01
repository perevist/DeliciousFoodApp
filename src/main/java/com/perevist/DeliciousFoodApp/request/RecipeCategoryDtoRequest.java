package com.perevist.DeliciousFoodApp.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RecipeCategoryDtoRequest {
    @NotBlank
    private String name;
}
