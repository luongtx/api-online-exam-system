package com.luongtx.oes.utils.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.luongtx.oes.dto.AnswerDTO;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Question;

public class QuestionConverter {
    public static QuestionDTO convertEntityToDTO(Question question) {
        Long categoryId = null;
        Long examId = null;
        if (question.getCatalog() != null) {
            categoryId = question.getCatalog().getId();
        }
        if (question.getExam() != null) {
            examId = question.getExam().getId();
        }
        List<AnswerDTO> answerDTOs = question.getAnswers().stream()
                .map((answer) -> AnswerConverter.convertEntityToDTO(answer))
                .collect(Collectors.toList());
        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .examId(examId)
                .catalogId(categoryId)
                .answers(answerDTOs)
                .build();
    }
}
