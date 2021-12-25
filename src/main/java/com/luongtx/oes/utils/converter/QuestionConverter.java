package com.luongtx.oes.utils.converter;

import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Question;

public class QuestionConverter {
    public static QuestionDTO convertEntityToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .examId(question.getExam().getId())
                .categoryId(question.getCategory().getId())
                .build();
    }
}
