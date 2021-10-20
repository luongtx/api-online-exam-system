package com.luongtx.oes.service.utils;

import com.luongtx.oes.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class ImageUtils {
    public static String encodeToBased64(String path) {
        String binary = null;
        try {
            if (path == null) {
                path = AppConstants.DEFAULT_PROFILE_IMAGE_PATH;
            }
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, getFileExtension(path), bos);
            byte[] bytes = Base64.getEncoder().encode(bos.toByteArray());
            binary = AppConstants.BASE64PREFIX + new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return binary;
    }

    static String getFileExtension(String path) {
        int indexOfDot = path.indexOf(".");
        if (indexOfDot == -1) {
            return "";
        }
        return path.substring(indexOfDot + 1);
    }
}
