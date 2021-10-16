package com.luongtx.oes.service;

import com.luongtx.oes.dto.ProfileDTO;

public interface ProfileService {
    ProfileDTO getCurrentUserProfile(String userToken);

    void updateCurrentUserProfile(String userToken, ProfileDTO profileDTO);
}
