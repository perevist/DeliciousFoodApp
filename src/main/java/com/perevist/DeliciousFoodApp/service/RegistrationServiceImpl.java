package com.perevist.DeliciousFoodApp.service;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import com.perevist.DeliciousFoodApp.mail.MailService;
import com.perevist.DeliciousFoodApp.model.Authority;
import com.perevist.DeliciousFoodApp.model.User;
import com.perevist.DeliciousFoodApp.model.VerificationToken;
import com.perevist.DeliciousFoodApp.repository.AuthorityRepository;
import com.perevist.DeliciousFoodApp.repository.UserRepository;
import com.perevist.DeliciousFoodApp.repository.VerificationTokenRepository;
import com.perevist.DeliciousFoodApp.request.RegistrationRequest;
import com.perevist.DeliciousFoodApp.service.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.perevist.DeliciousFoodApp.model.Authority.AuthorityName.ROLE_USER;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.url}")
    private String applicationUrl;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        validateIfUsernameAndEmailExist(registrationRequest.getUsername(), registrationRequest.getEmail());
        User user = createUserFromRegistrationRequest(registrationRequest);

        userRepository.save(user);

        String verificationToken = generateVerificationToken(user);
        sentAccountActivationEmail(user.getEmail(), verificationToken);
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        Authority authority = authorityRepository.findByName(ROLE_USER);
        user.setAuthorities(Set.of(authority));
        user.setEnabled(false);
        return user;
    }

    private void validateIfUsernameAndEmailExist(String username, String email) {
        if(userRepository.existsByUsername(username)) {
            throw new DeliciousFoodAppException(Error.USERNAME_ALREADY_EXISTS);
        }
        if(userRepository.existsByEmail(email)) {
            throw new DeliciousFoodAppException(Error.EMAIL_ALREADY_EXISTS);
        }
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new DeliciousFoodAppException(Error.VERIFICATION_TOKEN_IS_NOT_VALID));

        enableUserAccount(verificationToken);
    }

    private void sentAccountActivationEmail(String email, String verificationToken) {
        String subject = "Please activate your Account";
        String content = new StringBuilder()
                .append("Hello,\n\n")
                .append("Welcome to the DeliciousFoodApp. Click on the below url to activate your account:\n")
                .append(applicationUrl)
                .append("/api/registration/accountVerification/")
                .append(verificationToken)
                .toString();
        mailService.sendMail(subject, email, content);
    }

    @Transactional
    public void enableUserAccount(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
