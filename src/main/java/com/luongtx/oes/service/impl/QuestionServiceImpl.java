package com.luongtx.oes.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.luongtx.oes.dto.AnswerDTO;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Answer;
import com.luongtx.oes.entity.Catalog;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.CatalogRepo;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.service.QuestionService;
import com.luongtx.oes.utils.converter.AnswerConverter;
import com.luongtx.oes.utils.converter.QuestionConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    CatalogRepo catalogRepo;

    @Override
    public Page<QuestionDTO> findAll(Pageable pageable, String searchKey) {
        return questionRepo.findAll(pageable, searchKey)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public Page<QuestionDTO> findAllExcludeCatalog(Pageable pageable, String searchKey, Long catalogId) {
        return questionRepo.findAllExceptCatalog(pageable, searchKey, catalogId)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public void save(QuestionDTO dto) {
        try {
            Question question = dto.getId() != null ? questionRepo.getById(dto.getId()) : new Question();
            Exam exam = dto.getExamId() != null ? examRepo.getById(dto.getExamId()) : null;
            Catalog catalog = dto.getCatalogId() != null ? catalogRepo.getById(dto.getCatalogId()) : null;
            question.setExam(exam);
            question.setCatalog(catalog);
            question.setContent(dto.getContent());
            List<Answer> answers = new ArrayList<>();
            for (AnswerDTO answerDTO : dto.getAnswers()) {
                Answer answer = AnswerConverter.convertDTOToEntity(answerDTO);
                answer.setQuestion(question);
                answers.add(answer);
            }
            question.setAnswers(answers);
            questionRepo.save(question);
        } catch (Exception e) {
            log.error(String.format("Error while saving question: %s", dto), e);
        }
    }

    @Override
    public Page<QuestionDTO> findAllExcludeExam(Pageable pageable, String searchKey, Long examId) {
        return questionRepo.findAllExceptExam(pageable, searchKey, examId)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public Page<QuestionDTO> findAllByCatalog(Pageable pageable, String searchKey, Long catalogId) {
        return questionRepo.findAllByCatalog(catalogId, pageable, searchKey)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public Page<QuestionDTO> findAllByExam(Pageable pageable, String searchKey, Long examId) {
        return questionRepo.findAllByExam(examId, pageable, searchKey)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepo.deleteById(questionId);
    }

    @Override
    public void deleteQuestion(Long questionId, Long catalogId, Long examId) {
        Question question = questionRepo.getById(questionId);
        if (catalogId != null) {
            question.setCatalog(null);
            questionRepo.save(question);
        } else if (examId != null) {
            question.setExam(null);
            questionRepo.save(question);
        } else {
            questionRepo.delete(question);
        }
    }
}
