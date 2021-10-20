package com.luongtx.oes.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUtils {
    public static String uploadFile(MultipartFile imageFile, String uploadPath) {
        if (imageFile == null) return null;
        String uploadedFilePath = null;
        try {
            String imageFileName = imageFile.getOriginalFilename();
            Path absoluteFilePath = Paths.get(uploadPath + imageFileName);
            Files.copy(imageFile.getInputStream(), absoluteFilePath,
                    StandardCopyOption.REPLACE_EXISTING);
            uploadedFilePath = absoluteFilePath.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return uploadedFilePath;
    }
}
