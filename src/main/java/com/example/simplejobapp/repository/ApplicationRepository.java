package com.example.simplejobapp.repository;

import com.example.simplejobapp.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Optional<Application> findFirstByEmailAddress(String emailAddress);
    Optional<Application> findFirstByPhoneNumber(String phoneNumber);
}
