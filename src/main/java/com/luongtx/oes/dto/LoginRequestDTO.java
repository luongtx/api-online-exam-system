package com.luongtx.oes.dto;

import com.luongtx.oes.constants.ValidationConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = ValidationConstant.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = ValidationConstant.REQUIRED_PASSWORD)
    private String password;
}
