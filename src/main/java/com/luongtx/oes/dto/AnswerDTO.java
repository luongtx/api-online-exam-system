package com.luongtx.oes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnswerDTO {
    private Long id;
    private String content;
    private Boolean isCorrect;
}
