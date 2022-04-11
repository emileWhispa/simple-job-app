package com.example.simplejobapp.controllers;

import com.example.simplejobapp.enums.ResultCodeEnum;
import com.example.simplejobapp.models.User;
import com.example.simplejobapp.repository.UserRepository;
import com.example.simplejobapp.request.LoginRequest;
import com.example.simplejobapp.request.Result;
import com.example.simplejobapp.security.services.UserDetailsImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.simplejobapp.security.jwt.JwtUtils;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    //Sign in using email/phone and password
    @PostMapping(value = "/sign-in",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Optional<User> byAccount = userRepository.findFirstByUsername(loginRequest.getUsername());
        Result<Object> notFound = new Result<>(ResultCodeEnum.VALIDATE_ERROR.getCode(), "User not found");


        if (byAccount.isEmpty()) {
            return ResponseEntity.badRequest().body(notFound);
        }


        User userInfo = byAccount.get();


        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userInfo.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            Result<Object> invalidUsernameOrPassword = new Result<>(ResultCodeEnum.VALIDATE_ERROR.getCode(), "Invalid username or password");

            return ResponseEntity.badRequest().body(invalidUsernameOrPassword);
        }


        //Generate token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());


        userInfo.setToken(jwt);





        return ResponseEntity.ok(new Result<>(userInfo));
    }

}
