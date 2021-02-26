package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.request.RegistrationRequest;
import com.perevist.DeliciousFoodApp.service.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        registrationService.registerUser(registrationRequest);
    }

    @GetMapping("/accountVerification/{token}")
    public void verifyAccount(@PathVariable String token) {
        registrationService.verifyAccount(token);
    }
}
