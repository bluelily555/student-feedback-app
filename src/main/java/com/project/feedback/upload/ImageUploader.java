package com.project.feedback.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class ImageUploader {
    private final Resource imagesFolder;

    public ImageUploader(ResourceLoader resourceLoader) {
        imagesFolder = resourceLoader.getResource("file:images/");
    }

    public String upload(MultipartFile file) {
        String uuid = null;
        if (file.isEmpty()) return null;

        try{
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            uuid = UUID.randomUUID() + "." + ext;
            String imagePath = imagesFolder.getFile().getAbsolutePath() + File.separator + uuid;
            file.transferTo(new File(imagePath));
            log.info("업로드 완료 file={}", uuid);
            return uuid;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        log.info("업로드 실패");
        return uuid;
    }
}
