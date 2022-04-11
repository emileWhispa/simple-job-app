package com.example.simplejobapp.services;

import com.example.simplejobapp.enums.ApplicationStatus;
import com.example.simplejobapp.models.Application;
import com.example.simplejobapp.repository.ApplicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> top10Applications(){
        return applicationRepository.findAll(PageRequest.of(0,10)).toList();
    }

    public Optional<Application> findById(Long id){
        return applicationRepository.findById(id);
    }

    public Optional<Application> findByEmail(String email){
        return applicationRepository.findFirstByEmailAddress(email);
    }

    public Optional<Application> findByPhone(String phone){
        return applicationRepository.findFirstByPhoneNumber(phone);
    }

    public Application save(Application application){
        return applicationRepository.save(application);
    }

    public Application submitApplication(Application application, MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis() + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        FileUploadHelper.saveFile("cv",fileName,file);

        application.setCvAttachment(fileName);

        application.setStatus(ApplicationStatus.PENDING.name());

        return applicationRepository.save(application);
    }
}
