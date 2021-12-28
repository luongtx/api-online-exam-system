package com.luongtx.oes.utils.converter;

import com.luongtx.oes.dto.AnswerDTO;
import com.luongtx.oes.entity.Answer;
import com.luongtx.oes.entity.Question;

public class AnswerConverter {
    public static Answer convertDTOToEntity(AnswerDTO dto, Question question) {
        return Answer.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .question(question)
                .correct(dto.getCorrect())
                .build();
    }

    public static AnswerDTO convertEntityToDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .correct(answer.getCorrect())
                .build();
    }
}
