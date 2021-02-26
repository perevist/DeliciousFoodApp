package com.perevist.DeliciousFoodApp.service.interfaces;

import com.perevist.DeliciousFoodApp.request.RegistrationRequest;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);
    void verifyAccount(String token);
}
