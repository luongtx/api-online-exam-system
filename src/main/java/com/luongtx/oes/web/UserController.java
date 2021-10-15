package com.luongtx.oes.web;

import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.service.ProfileService;
import com.luongtx.oes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getUserProfile(@RequestHeader("Authorization") String userToken) {
        ProfileDTO profileDTO = profileService.getCurrentUserProfile(userToken);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateUserProfile(@RequestHeader("Authorization") String userToken,
                                                    @Valid @RequestBody ProfileDTO profileDTO) {
        String result = profileService.updateCurrentUserProfile(userToken, profileDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
