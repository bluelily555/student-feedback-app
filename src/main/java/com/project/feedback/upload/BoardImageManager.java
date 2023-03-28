package com.project.feedback.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Slf4j
public class BoardImageManager extends AbstractFileManager {

    public BoardImageManager(String imagesFolderPath) {
        super(imagesFolderPath);
    }

    @Override
    public String upload(MultipartFile file) {
        log.info("질문 이미지 업로드");
        return super.upload(file);
    }

    @Override
    public boolean delete(String fileName) {
        log.info("질문 이미지 삭제");
        return super.delete(fileName);
    }

    public boolean deleteAll(Collection<String> imageNames) {
        log.info("질문 이미지 삭제");
        boolean result = true;
        for (String imageName : imageNames) {
            result &= super.delete(imageName);
        }
        return result;
    }
}
