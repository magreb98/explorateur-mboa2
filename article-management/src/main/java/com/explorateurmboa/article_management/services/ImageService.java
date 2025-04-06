package com.explorateurmboa.article_management.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${image.upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) {
        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
        }
    }

    public void deleteImage(String filePath) {
        try {
            Path path = Paths.get(uploadDir, filePath.replace("/uploads/", ""));
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'image", e);
        }
    }

    public ResponseEntity<byte[]> getImage(String filePath) {
        try{
            Path path = Paths.get(uploadDir+filePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return (ResponseEntity<byte[]>) ResponseEntity.ok().contentType(MediaType.ALL).body(imageBytes);
        }catch (IOException e){
            throw new RuntimeException("Erreur lors de la lecture de l'image", e);
        }
    }
}
