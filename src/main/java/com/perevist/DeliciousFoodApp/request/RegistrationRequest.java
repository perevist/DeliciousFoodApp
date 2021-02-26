package com.perevist.DeliciousFoodApp.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class RegistrationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
}
