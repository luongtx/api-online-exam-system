package com.luongtx.oes.service;

import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.CategoryRepo;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public void save(QuestionDTO questionDTO) {
        Question question = convertToEntity(questionDTO);
        questionRepo.save(question);
    }

    Question convertToEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setContent(questionDTO.getContent());
        question.setExam(examRepo.getById(questionDTO.getExamId()));
        question.setCategory(categoryRepo.getById(questionDTO.getCategoryId()));
        return question;
    }
}
