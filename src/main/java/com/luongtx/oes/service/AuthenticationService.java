package com.luongtx.oes.service;

import com.luongtx.oes.dto.LoginDTO;
import com.luongtx.oes.dto.RegisterDTO;
import com.luongtx.oes.exception.ApplicationUserException;

public interface AuthenticationService {
    String login(LoginDTO loginDTO) throws ApplicationUserException;

    String register(RegisterDTO registerDTO) throws ApplicationUserException;
}
