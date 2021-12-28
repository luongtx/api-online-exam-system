package com.luongtx.oes.web;

import javax.validation.Valid;

import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<ProfileDTO> getUserProfile(@RequestHeader("Authorization") String userToken) {
        ProfileDTO profileDTO = profileService.getCurrentUserProfile(userToken);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUserProfile(@RequestHeader("Authorization") String userToken,
                                                  @Valid @RequestBody ProfileDTO profileDTO) {
        profileService.updateCurrentUserProfile(userToken, profileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Void> uploadProfileImage(@RequestHeader("Authorization") String userToken,
                                                   @RequestBody MultipartFile file) {
        profileService.uploadProfileImage(userToken, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
