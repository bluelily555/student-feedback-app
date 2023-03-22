package com.project.feedback.upload;

import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Slf4j
public class ImageManager {
    private final String imagesFolderPath;

    public ImageManager(ResourceLoader resourceLoader) throws IOException {
        imagesFolderPath = resourceLoader.getResource("file:images/").getFile().getAbsolutePath();
    }

    public String upload(MultipartFile file) {
        if (file.isEmpty()) return null;

        try{
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uuid = UUID.randomUUID() + "." + ext;
            String imagePath = imagesFolderPath + File.separator + uuid;
            file.transferTo(new File(imagePath));
            log.info("업로드 완료 file={}", uuid);
            return uuid;
        } catch (IOException e) {
            log.info("업로드 실패");
            log.error(e.getMessage(), e);
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }

    public boolean delete(String imageName) {
        File file = new File(imagesFolderPath + File.separator + imageName);
        if (file.exists()) {
            if(file.delete()) {
                log.info("이미지 삭제 file={}", imageName);
                return true;
            }
        }
        log.info("이미지 삭제 실패 file={}", imageName);
        return false;
    }

    public boolean deleteAll(Collection<String> imageNames) {
        boolean result = true;
        for (String imageName : imageNames) {
            result &= delete(imageName);
        }
        return result;
    }
}
