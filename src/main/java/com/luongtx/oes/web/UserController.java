package com.luongtx.oes.web;

import com.luongtx.oes.dto.ProfileDTO;
import com.luongtx.oes.service.ProfileService;
import com.luongtx.oes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
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
    public ResponseEntity<Void> updateUserProfile(@RequestHeader("Authorization") String userToken,
                                                  @Valid @RequestBody ProfileDTO profileDTO) {
        profileService.updateCurrentUserProfile(userToken, profileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/upload-image")
    public ResponseEntity<Void> uploadProfileImage(@RequestHeader("Authorization") String userToken,
                                                   @RequestBody MultipartFile file) {
        profileService.uploadProfileImage(userToken, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
