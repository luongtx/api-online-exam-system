package com.luongtx.oes.service;

import com.luongtx.oes.dto.LoginRequestDTO;
import com.luongtx.oes.dto.LoginResponseDTO;
import com.luongtx.oes.dto.RegisterRequestDTO;
import com.luongtx.oes.dto.RegisterResponseDTO;
import com.luongtx.oes.exception.ApplicationUserException;

public interface AuthenticationService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws ApplicationUserException;

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) throws ApplicationUserException;
}
