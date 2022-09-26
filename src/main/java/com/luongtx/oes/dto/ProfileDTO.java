package com.luongtx.oes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luongtx.oes.constants.ValidationConstant;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    @NotBlank(message = ValidationConstant.REQUIRED_FULL_NAME)
    private String fullName;

    private String gender;

    private String email;

    private String phoneNo;

    private String imageSrc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

}
