package com.luongtx.oes.service.impl;

import java.util.Optional;

import com.luongtx.oes.config.ResourcePathConfig;
import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.repository.specification.ProfileSpecs;
import com.luongtx.oes.security.JwtTokenUtil;
import com.luongtx.oes.service.ProfileService;
import com.luongtx.oes.utils.FileUtils;
import com.luongtx.oes.utils.ImageUtils;
import com.luongtx.oes.utils.converter.ProfileConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    ResourcePathConfig pathConfig;

    @Override
    public ProfileDTO getCurrentUserProfile(String userToken) {
        Profile profile = retrieveProfileFromToken(userToken);
        String profileImage = resolveProfileImage(profile.getImageSrc());
        profile.setImageSrc(profileImage);
        // log.debug(profile);
        return ProfileConverter.convertToProfileDTO(profile);
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
            // log.debug(profile);
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
            String uploadedFilePath = FileUtils.uploadFile(file, pathConfig.getUploadPath());
            if (uploadedFilePath != null) {
                profile.setImageSrc(uploadedFilePath);
            }
            profileRepo.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    Profile retrieveProfileFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        Specification<Profile> specification = ProfileSpecs.hasUsername(username);
        Optional<Profile> optional = profileRepo.findOne(specification);
        return optional.get();
    }

    String resolveProfileImage(String imageSrc) {
        if (imageSrc == null) {
            imageSrc = pathConfig.getUploadPath() + pathConfig.getDefaultProfileImage();
        }
        return ImageUtils.encodeToBased64(imageSrc);
    }
}
