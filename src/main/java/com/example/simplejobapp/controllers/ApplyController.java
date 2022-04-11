package com.example.simplejobapp.controllers;

import com.example.simplejobapp.enums.ResultCodeEnum;
import com.example.simplejobapp.models.Application;
import com.example.simplejobapp.request.Result;
import com.example.simplejobapp.services.ApplicationService;
import com.example.simplejobapp.services.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/apply/")
public class ApplyController {
    private final ApplicationService applicationService;
    private final FilesStorageService filesStorageService;

    public ApplyController(ApplicationService applicationService, FilesStorageService filesStorageService) {
        this.applicationService = applicationService;
        this.filesStorageService = filesStorageService;
    }

    @PostMapping("submit")
    public ResponseEntity<Result<Application>> submit(@ModelAttribute("Application") Application application,@RequestPart("attachment") MultipartFile file){

        if(application == null || file == null){
            return ResponseEntity.badRequest().body(new Result<>(ResultCodeEnum.ERROR.getCode(),"Request Body is missing"));
        }

        if(applicationService.findByEmail(application.getEmailAddress()).isPresent()){
            return ResponseEntity.badRequest().body(new Result<>(ResultCodeEnum.VALIDATE_ERROR.getCode(),"Application with this email address already exists"));
        }

        if(applicationService.findByPhone(application.getPhoneNumber()).isPresent()){
            return ResponseEntity.badRequest().body(new Result<>(ResultCodeEnum.VALIDATE_ERROR.getCode(),"Application with this phone number already exists"));
        }

        Application app;
        try {
            app = applicationService.submitApplication(application,file);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new Result<>(ResultCodeEnum.ERROR.getCode(),e.getMessage()));
        }

        return ResponseEntity.ok(new Result<>(app,"Application submitted successfully, wait for approval"));
    }

    @GetMapping("download/cv/attachment/{id}")
    public Object download(@PathVariable("id") long id, RedirectAttributes ra, HttpServletRequest request) throws IOException
    {
        Optional<Application> applicationOptional = applicationService.findById(id);

        if (applicationOptional.isEmpty()) {
            ra.addFlashAttribute("error", "Purchase is not found!");

            return "redirect:" + request.getHeader("Referer");
        }

        Application application = applicationOptional.get();

        Resource resource = filesStorageService.load("cv/" + application.getCvAttachment());
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + application.getCvAttachment() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
