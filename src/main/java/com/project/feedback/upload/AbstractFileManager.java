package com.project.feedback.upload;

import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public abstract class AbstractFileManager implements FileManager {
    private final String path;

    public AbstractFileManager(String path) {
        mkdir(path);
        this.path = path;
    }

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) return null;

        try{
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uuid = UUID.randomUUID() + "." + ext;
            String filePath = path + File.separator + uuid;
            file.transferTo(new File(filePath));
            log.info("파일 업로드 완료 file={}", filePath);
            return uuid;
        } catch (IOException e) {
            log.info("파일 업로드 실패");
            log.error(e.getMessage(), e);
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public boolean delete(String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            if(file.delete()) {
                log.info("파일 삭제 완료 file={}", file.getAbsolutePath());
                return true;
            }
        }
        log.info("파일 삭제 실패 file={}", file.getAbsolutePath());
        return false;
    }

    private static void mkdir(String absolutePath) {
        File folder = new File(absolutePath);
        if (!folder.exists()) folder.mkdirs();
    }
}
