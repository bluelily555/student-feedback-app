package com.project.feedback.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@Configuration
public class UploaderConfig {
    @Bean
    public ImageUploader imageUploader(ResourceLoader resourceLoader) throws IOException {
        // image 디렉토리 생성.
        File root = resourceLoader.getResource("file:").getFile();
        String imagePath = root.getAbsolutePath() + File.separator + "images" + File.separator;
        File imageFolder = new File(imagePath);
        if (!imageFolder.exists()) imageFolder.mkdirs();
        return new ImageUploader(resourceLoader);
    }
}
