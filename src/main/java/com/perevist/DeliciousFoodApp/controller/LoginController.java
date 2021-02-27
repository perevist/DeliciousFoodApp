package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest loginRequest) {;
    }
}
