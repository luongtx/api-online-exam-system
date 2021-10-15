package com.luongtx.oes.service.impl;

import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepo examRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Override
    public List<Exam> findAll() {
        return examRepo.findAll();
    }

    @Override
    public Exam findById(Long id) {
        return examRepo.findById(id).orElse(new Exam());
    }

    @Override
    public Exam findDetailById(Long id) {
        Exam exam = findById(id);
        int numberOfQuestions = findNumberOfQuestions(id);
        exam.setNumberOfQuestions(numberOfQuestions);
        return exam;
    }

    @Override
    public ExamResultDTO evaluateResult(Long examId, List<List<Long>> listAnswers) {
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
        log.info(resultDTO.toString());
        return resultDTO;
    }

    @Override
    public Exam getById(Long id) {
        return examRepo.getById(id);
    }

    @Override
    public void add(Exam exam) {

    }

    @Override
    public Exam deleteById(Long id) {
        return null;
    }

    @Override
    public void update(Exam exam) {

    }

    @Override
    public List<Question> findQuestionsByExamId(Long id) {
        return examRepo.findById(id).orElse(new Exam()).getQuestions();
    }

    public int findNumberOfQuestions(Long id) {
        return questionRepo.countQuestionsByExamId(id);
    }
}
