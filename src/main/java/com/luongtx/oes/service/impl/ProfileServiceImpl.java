package com.luongtx.oes.service.impl;


import com.luongtx.oes.constants.ApplicationMessageConstant;
import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {


    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public ProfileDTO getCurrentUserProfile(String userToken) {
        Profile profile = retrieveProfileFromToken(userToken);
        log.info(profile.toString());
        return convertToProfileDTO(profile);
    }

    @Override
    public String updateCurrentUserProfile(String userToken, ProfileDTO profileDTO) {
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
        return ApplicationMessageConstant.SUCCESSFULLY_UPDATE_PROFILE;
    }

    ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setGender(profile.getGender());
        profileDTO.setPhoneNo(profile.getPhoneNo());
        profileDTO.setBirthDay(profile.getBirthDay());
        return profileDTO;
    }

    Profile retrieveProfileFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return profileRepo.findByUsername(username);
    }
}
