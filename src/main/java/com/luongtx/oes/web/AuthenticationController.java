package com.luongtx.oes.web;


import com.luongtx.oes.dto.LoginRequestDTO;
import com.luongtx.oes.dto.LoginResponseDTO;
import com.luongtx.oes.dto.RegisterRequestDTO;
import com.luongtx.oes.dto.RegisterResponseDTO;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws ApplicationUserException {
        LoginResponseDTO responseDTO = authenticationService.login(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) throws ApplicationUserException {
        RegisterResponseDTO result = authenticationService.register(registerRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
