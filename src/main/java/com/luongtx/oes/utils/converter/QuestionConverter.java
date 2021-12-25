package com.luongtx.oes.utils.converter;

import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Question;

public class QuestionConverter {
    public static QuestionDTO convertEntityToDTO(Question question) {
        Long categoryId = null;
        Long examId = null;
        if(question.getCategory() != null) {
            categoryId = question.getCategory().getId();
        }
        if(question.getExam() != null) {
            examId = question.getExam().getId();
        }
        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .examId(examId)
                .categoryId(categoryId)
                .build();
    }
}
