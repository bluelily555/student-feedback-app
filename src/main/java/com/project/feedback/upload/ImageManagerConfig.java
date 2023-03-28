package com.project.feedback.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@Configuration
public class ImageManagerConfig {
    @Bean
    public BoardImageManager boardImageManager(ResourceLoader resourceLoader) throws IOException {
        String IMAGE_FOLDER_PATH = File.separator + "images";
        File root = resourceLoader.getResource("file:").getFile();
        String absoluteImagePath = root.getAbsolutePath() + IMAGE_FOLDER_PATH;
        return new BoardImageManager(absoluteImagePath);
    }
}
