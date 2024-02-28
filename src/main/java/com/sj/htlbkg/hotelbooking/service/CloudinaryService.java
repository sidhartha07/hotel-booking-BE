package com.sj.htlbkg.hotelbooking.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImage(MultipartFile image, String folderName);
    void deleteImage(String publicId);
}
