package com.luongtx.oes.utils.converter;

import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;

public class ProfileConverter {
    public static ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setGender(profile.getGender());
        profileDTO.setPhoneNo(profile.getPhoneNo());
        profileDTO.setBirthDay(profile.getBirthDay());
        profileDTO.setImageSrc(profile.getImageSrc());
        return profileDTO;
    }
}
