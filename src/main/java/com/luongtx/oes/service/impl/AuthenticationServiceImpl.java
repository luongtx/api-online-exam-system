package com.luongtx.oes.service.impl;

import com.luongtx.oes.constants.ApplicationMessageConstant;
import com.luongtx.oes.dto.LoginRequestDTO;
import com.luongtx.oes.dto.LoginResponseDTO;
import com.luongtx.oes.dto.RegisterRequestDTO;
import com.luongtx.oes.dto.RegisterResponseDTO;
import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.entity.User;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.repository.RoleRepo;
import com.luongtx.oes.repository.UserRepo;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginDTO) throws ApplicationUserException {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ApplicationUserException(ApplicationMessageConstant.INCORRECT_USER_NAME_PASSWORD);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        responseDTO.setToken(jwtTokenUtil.generateToken(userDetails));
        return responseDTO;
    }

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO registerDTO) throws ApplicationUserException {
        RegisterResponseDTO responseDTO = new RegisterResponseDTO();
        try {
            checkUserAvailability(registerDTO);
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRoles(new HashSet<>(Collections.singletonList(roleRepo.getById(1L))));
            user.setRegDate(LocalDateTime.now());
            user.setModDate(LocalDateTime.now());
            User savedUser = userRepo.save(user);
            Profile profile = constructProfile(registerDTO, savedUser);
            profileRepo.save(profile);
        } catch (ApplicationUserException e) {
            log.error(e.getMessage());
            throw new ApplicationUserException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        responseDTO.setMessage(ApplicationMessageConstant.SUCCESSFULLY_REGISTERED);
        return responseDTO;
    }

    private void checkUserAvailability(RegisterRequestDTO registerDTO) throws ApplicationUserException {
        if (userRepo.findUserByUsername(registerDTO.getUsername()) != null) {
            throw new ApplicationUserException(ApplicationMessageConstant.TAKEN_USER_NAME);
        }
    }

    Profile constructProfile(RegisterRequestDTO registerDTO, User user) {
        Profile profile = new Profile();
        profile.setEmail(registerDTO.getEmail());
        profile.setFullName(registerDTO.getFullName());
        profile.setGender(registerDTO.getGender());
        profile.setBirthDay(registerDTO.getBirthDay());
        profile.setPhoneNo(registerDTO.getPhoneNo());
        profile.setUser(user);
        return profile;
    }
}
