package com.luongtx.oes.service.impl;

import com.luongtx.oes.dto.ExamResultDTO;
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
        return examRepository.findById(id).orElse(new Exam());
    }

    @Override
    public Exam findDetailById(Integer id) {
        Exam exam = findById(id);
        int numberOfQuestions = findNumberOfQuestions(id);
        exam.setNumberOfQuestions(numberOfQuestions);
        return exam;
    }

    @Override
    public ExamResultDTO evaluateResult(Integer examId, List<List<Integer>> listAnswers) {
        ExamResultDTO resultDTO = new ExamResultDTO();
        Exam exam = getById(examId);
        List<Question> questions = exam.getQuestions();
        int corrects = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (listAnswers.get(i).equals(questions.get(i).getCorrectAnswers())) {
                corrects++;
            }
        }
        resultDTO.setScore((100 * corrects) / questions.size());
        resultDTO.setStatus(resultDTO.getScore() >= exam.getPassingScore());
        return resultDTO;
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

    public int findNumberOfQuestions(Integer id) {
        return questionRepository.countQuestionsByExamId(id);
    }
}
