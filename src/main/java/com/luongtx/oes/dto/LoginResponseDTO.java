package com.luongtx.oes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {
    private String token;
    private List<String> roles;
}
