package com.sj.htlbkg.hotelbooking.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryServiceImpl.class);
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile image, String folderName) {
        try {
            Map<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedImage = cloudinary.uploader().upload(image.getBytes(), options);
            String publicId = (String) uploadedImage.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            logger.warn("image upload failed with Error - {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            Map map = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            logger.info("deleted image with publicId : {}", publicId);
        } catch (IOException e) {
            logger.warn("image deletion failed with Error - {}", e.getMessage());
        }
    }
}
