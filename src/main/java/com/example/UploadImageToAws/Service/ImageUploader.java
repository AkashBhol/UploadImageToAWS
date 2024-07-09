package com.example.UploadImageToAws.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    String uploadImage(MultipartFile multipartFile);

    List<String>  allFiles();

    String presignedUrl(String fileName);
}
