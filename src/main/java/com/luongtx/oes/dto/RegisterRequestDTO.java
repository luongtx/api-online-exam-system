package com.luongtx.oes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luongtx.oes.constants.ValidationConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = ValidationConstant.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = ValidationConstant.REQUIRED_PASSWORD)
    private String password;

    @NotBlank(message = ValidationConstant.REQUIRED_FULL_NAME)
    private String fullName;

    private String gender;
    private String email;
    private String phoneNo;
    //Json Date value cannot be deserialized to LocalDate without this annotation
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private String imageSrc;

}
