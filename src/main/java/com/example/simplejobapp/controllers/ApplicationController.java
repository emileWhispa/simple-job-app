package com.example.simplejobapp.controllers;

import com.example.simplejobapp.enums.ApplicationStatus;
import com.example.simplejobapp.enums.ResultCodeEnum;
import com.example.simplejobapp.models.Application;
import com.example.simplejobapp.request.Result;
import com.example.simplejobapp.services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications/")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("list")
    private ResponseEntity<Result<List<Application>>> top10(){
        return ResponseEntity.ok(new Result<>(applicationService.top10Applications()));
    }


    @GetMapping("update/status/{id}/{status}")
    private ResponseEntity<Result<Application>> updateStatus(@PathVariable("id") Long id,@PathVariable("status") Integer status){

        Optional<Application> applicationOptional = applicationService.findById(id);

        if(applicationOptional.isEmpty()){
            return ResponseEntity.badRequest().body(new Result<>(ResultCodeEnum.ERROR.getCode(),"Application is not found"));
        }

        Application application = applicationOptional.get();

        application.setStatus(status == 1 ? ApplicationStatus.PASSED.name() :  ApplicationStatus.DROPPED.name());





        return ResponseEntity.ok(new Result<>(applicationService.save(application),"Application status changed successfully"));
    }


}
