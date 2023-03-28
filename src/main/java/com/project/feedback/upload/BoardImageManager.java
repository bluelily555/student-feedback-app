package com.project.feedback.upload;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class BoardImageManager extends AbstractFileManager {

    public BoardImageManager(String imagesFolderPath) {
        super(imagesFolderPath);
    }

    public boolean deleteAll(Collection<String> imageNames) {
        boolean result = true;
        for (String imageName : imageNames) {
            result &= delete(imageName);
        }
        return result;
    }
}
