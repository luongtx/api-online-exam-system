package com.luongtx.oes.utils.converter;

import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.entity.Profile;

public class ProfileConverter {
    public static ProfileDTO convertToProfileDTO(Profile profile) {
        return ProfileDTO.builder()
                .email(profile.getEmail())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .phoneNo(profile.getPhoneNo())
                .birthDay(profile.getBirthDay())
                .imageSrc(profile.getImageSrc())
                .build();
    }
}
