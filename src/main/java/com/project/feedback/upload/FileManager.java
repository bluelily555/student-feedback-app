package com.project.feedback.upload;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {
    String upload(MultipartFile file);
    boolean delete(String fileName);
}
