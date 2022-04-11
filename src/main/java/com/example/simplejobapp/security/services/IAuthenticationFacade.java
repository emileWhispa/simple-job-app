package com.example.simplejobapp.security.services;

import org.springframework.security.core.Authentication;

interface IAuthenticationFacade {
   Authentication getAuthentication();
}
