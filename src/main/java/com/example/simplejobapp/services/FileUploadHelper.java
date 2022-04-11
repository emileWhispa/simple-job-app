package com.example.simplejobapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {


    public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {

        Path uploadPath= Paths.get("./uploads/"+uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);

        }
        try ( InputStream inputStream=multipartFile.getInputStream()){

            Path filePath=uploadPath.resolve(filename);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("could not save this file "+filename);
        }
    }
    public static void deleteFile(String filepath)  {

        try {
            Files.delete(Paths.get("."+filepath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
