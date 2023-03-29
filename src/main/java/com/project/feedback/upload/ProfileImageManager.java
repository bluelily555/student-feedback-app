package com.project.feedback.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ProfileImageManager extends AbstractFileManager {
    public ProfileImageManager(String path) {
        super(path);
    }

    @Override
    public String upload(MultipartFile file) {
        log.info("프로필 이미지 업로드");
        return super.upload(file);
    }

    @Override
    public boolean delete(String fileName) {
        log.info("프로필 이미지 삭제");
        return super.delete(fileName);
    }
}
