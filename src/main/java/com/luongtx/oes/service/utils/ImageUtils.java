package com.luongtx.oes.service.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.luongtx.oes.constants.AppConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImageUtils {
    public static String encodeToBased64(String path) {
        String binary = null;
        try {
            log.info("[ImageUtils] path: {}", path);
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, getFileExtension(path), bos);
            byte[] bytes = Base64.getEncoder().encode(bos.toByteArray());
            binary = AppConstants.BASE64PREFIX + new String(bytes, StandardCharsets.UTF_8);
            log.debug(binary);
        } catch (Exception e) {
            log.error("[ImageUtils] EncodeBase64", e);
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

    public static void main(String[] args) {
        encodeToBased64("/home/luongtx/uploads/photo-1570612861542-284f4c12e75f.jpeg");
    }
}
