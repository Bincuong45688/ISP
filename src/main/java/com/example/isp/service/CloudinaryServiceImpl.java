package com.example.isp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.isp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder:isp/products}")
    private String defaultFolder;

    @Override
    public String uploadImage(MultipartFile file, String folder) {
        try {
            Map<?, ?> res = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder != null ? folder : defaultFolder,
                            "resource_type", "image",
                            "overwrite", true
                    )
            );
            return (String) res.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Upload Cloudinary failed: " + e.getMessage(), e);
        }
    }
    @Override
    public String uploadVideo(MultipartFile file, String folder) {
        try {
            Map<?, ?> res = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder != null ? folder : defaultFolder,
                            "resource_type", "video",
                            "overwrite", true
                    )
            );
            return (String) res.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Upload video to Cloudinary failed: " + e.getMessage(), e);
        }
    }
}
