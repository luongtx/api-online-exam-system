package com.luongtx.oes.service;

import com.luongtx.oes.dto.ProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileDTO getCurrentUserProfile(String userToken);

    void updateCurrentUserProfile(String userToken, ProfileDTO profileDTO);

    void uploadProfileImage(String userToken, MultipartFile file);
}
