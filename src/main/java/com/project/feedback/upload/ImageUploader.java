package com.project.feedback.upload;

import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
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
        if (file.isEmpty()) return null;

        try{
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uuid = UUID.randomUUID() + "." + ext;
            String imagePath = imagesFolder.getFile().getAbsolutePath() + File.separator + uuid;
            file.transferTo(new File(imagePath));
            log.info("업로드 완료 file={}", uuid);
            return uuid;
        } catch (IOException e) {
            log.info("업로드 실패");
            log.error(e.getMessage(), e);
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }
}
