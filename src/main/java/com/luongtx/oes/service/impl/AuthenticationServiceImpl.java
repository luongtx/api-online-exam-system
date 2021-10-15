package com.luongtx.oes.service.impl;

import com.luongtx.oes.constants.ApplicationMessageConstant;
import com.luongtx.oes.dto.LoginDTO;
import com.luongtx.oes.dto.RegisterDTO;
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
    public String login(LoginDTO loginDTO) throws ApplicationUserException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ApplicationUserException("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    @Transactional
    public String register(RegisterDTO registerDTO) throws ApplicationUserException {
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
        return ApplicationMessageConstant.SUCCESSFULLY_REGISTERED;
    }

    private void checkUserAvailability(RegisterDTO registerDTO) throws ApplicationUserException {
        if (userRepo.findUserByUsername(registerDTO.getUsername()) != null) {
            throw new ApplicationUserException("This username has already taken by another user");
        }
    }

    Profile constructProfile(RegisterDTO registerDTO, User user) {
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
