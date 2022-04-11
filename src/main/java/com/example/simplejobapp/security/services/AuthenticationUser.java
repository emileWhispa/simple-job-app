package com.example.simplejobapp.security.services;

import com.example.simplejobapp.models.User;
import com.example.simplejobapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationUser implements IAuthenticationFacade {
    private final UserRepository repository;
//    private static Logger logger = LoggerFactory.getLogger(AuthenticationUser.class);

    public AuthenticationUser(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User user() {
        String name = getAuthentication().getName();

        Optional<User> user = repository.findFirstByUsername(name);

        return user.orElse(null);
    }

    public Long userId() {
        return user().getId();
    }
}
