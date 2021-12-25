package com.luongtx.oes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private String content;
    private List<AnswerDTO> answers;
    private Long examId;
    private Long categoryId;
}
