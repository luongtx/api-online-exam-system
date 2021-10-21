package com.luongtx.oes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String content;
    private List<AnswerDTO> answers;
}
