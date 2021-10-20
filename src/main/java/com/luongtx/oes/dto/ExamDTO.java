package com.luongtx.oes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ExamDTO {

    private Long id;

    @NotBlank
    private String examCode;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer passingScore;

    //Serialized only
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bannerImageSource;

    private Integer numberOfQuestions;

}
