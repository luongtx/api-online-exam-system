package com.luongtx.oes.service.impl;


import com.luongtx.oes.constants.AppConstants;
import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.ProfileService;
import com.luongtx.oes.service.utils.FileUtils;
import com.luongtx.oes.service.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        Profile profile = retrieveProfileFromToken(userToken);
        String uploadedFilePath = FileUtils.uploadFile(file, uploadPath);
        if (uploadedFilePath != null) {
            profile.setImageSrc(uploadedFilePath);
        }
        profileRepo.save(profile);
    }

    ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setGender(profile.getGender());
        profileDTO.setPhoneNo(profile.getPhoneNo());
        profileDTO.setBirthDay(profile.getBirthDay());
        String base64Image = resolveProfileImage(profile.getImageSrc());
        profileDTO.setImageSrc(base64Image);
        return profileDTO;
    }

    Profile retrieveProfileFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return profileRepo.findByUsername(username);
    }

    String resolveProfileImage(String imageSrc) {
        if (imageSrc == null) {
            imageSrc = AppConstants.DEFAULT_PROFILE_IMAGE_PATH;
        }
        return ImageUtils.encodeToBased64(imageSrc);
    }
}
