package com.luongtx.oes.service.impl;


import com.luongtx.oes.constants.AppConstants;
import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {


    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${upload.path}")
    String uploadPath;

    @Override

    public ProfileDTO getCurrentUserProfile(String userToken) {
        Profile profile = retrieveProfileFromToken(userToken);
        log.info(profile.toString());
        return convertToProfileDTO(profile);
    }

    @Override
    public void updateCurrentUserProfile(String userToken, ProfileDTO profileDTO) {
        try {
            Profile profile = retrieveProfileFromToken(userToken);
            profile.setEmail(profileDTO.getEmail());
            profile.setFullName(profileDTO.getFullName());
            profile.setGender(profileDTO.getGender());
            profile.setPhoneNo(profileDTO.getPhoneNo());
            profile.setBirthDay(profileDTO.getBirthDay());
            log.info(profile.toString());
            profileRepo.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApplicationUserException(e.getMessage());
        }
    }

    @Override
    public void uploadProfileImage(String userToken, MultipartFile file) {
        try {
            Profile profile = retrieveProfileFromToken(userToken);
            String fileName = "avatar" + profile.getId() + ".jpg";
            Path targetPath = Paths.get(this.uploadPath + fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            profile.setImageSrc(targetPath.toString());
            profileRepo.save(profile);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ApplicationUserException(e.getMessage());
        }
    }

    ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setGender(profile.getGender());
        profileDTO.setPhoneNo(profile.getPhoneNo());
        profileDTO.setBirthDay(profile.getBirthDay());
        profileDTO.setImageSrc(encodeToBased64(profile.getImageSrc()));
        return profileDTO;
    }

    Profile retrieveProfileFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return profileRepo.findByUsername(username);
    }

    String encodeToBased64(String path) {
        String binary = null;
        try {
            if (path == null) {
                path = AppConstants.DEFAULT_PROFILE_IMAGE_PATH;
            }
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", bos);
            byte[] bytes = Base64.getEncoder().encode(bos.toByteArray());
            binary = AppConstants.BASE64PREFIX + new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return binary;
    }
}
