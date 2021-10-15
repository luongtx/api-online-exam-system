package com.luongtx.oes.web;


import com.luongtx.oes.dto.LoginDTO;
import com.luongtx.oes.dto.RegisterDTO;
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
@CrossOrigin
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws ApplicationUserException {
        String result = authenticationService.login(loginDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) throws ApplicationUserException {
        String result = authenticationService.register(registerDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
