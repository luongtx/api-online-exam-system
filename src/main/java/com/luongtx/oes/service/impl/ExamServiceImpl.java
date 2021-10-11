package com.luongtx.oes.service.impl;

import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.ExamRepository;
import com.luongtx.oes.repository.QuestionRepository;
import com.luongtx.oes.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(Integer id) {
        Exam exam = examRepository.findById(id).orElse(new Exam());
        exam.setNumberOfQuestions(findNumberOfQuestions(id));
        return exam;
    }

    @Override
    public Exam getById(Integer id) {
        return examRepository.getById(id);
    }

    @Override
    public void add(Exam exam) {

    }

    @Override
    public Exam deleteById(Integer id) {
        return null;
    }

    @Override
    public void update(Exam exam) {

    }

    @Override
    public List<Question> findQuestionsByExamId(Integer id) {
        return examRepository.findById(id).orElse(new Exam()).getQuestions();
    }

    @Override
    public int findNumberOfQuestions(Integer id) {
        return questionRepository.countQuestionsByExamId(id);
    }
}
