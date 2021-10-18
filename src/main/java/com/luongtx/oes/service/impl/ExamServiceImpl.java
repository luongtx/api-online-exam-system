package com.luongtx.oes.service.impl;

import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.entity.User;
import com.luongtx.oes.entity.UserExam;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.repository.UserExamRepo;
import com.luongtx.oes.repository.UserRepo;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    UserExamRepo userExamRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

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
    @Transactional
    public ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers) {
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
        resultDTO.setPassingScore(exam.getPassingScore());
        resultDTO.setStatus(resultDTO.getScore() >= exam.getPassingScore());
        resultDTO.setFinishedAt(LocalDateTime.now());
        log.info(resultDTO.toString());
        updateUserExamResult(userToken, examId, resultDTO);
        return resultDTO;
    }

    @Override
    public List<ExamResultDTO> getRecentUserExams(String userToken) {
        List<ExamResultDTO> examResultDTOS = new ArrayList<>();
        User user = getUserFromToken(userToken);
        List<UserExam> userExams = userExamRepo.getAllByUserIdOrderByRegDateDesc(user.getId());
        log.info(userExams.toString());
        for (UserExam userExam : userExams) {
            ExamResultDTO dto = new ExamResultDTO();
            Exam exam = getById(userExam.getExamId());
            dto.setExamId(userExam.getExamId());
            dto.setTitle(exam.getTitle());
            dto.setDescription(exam.getDescription());
            dto.setStatus(userExam.getStatus());
            dto.setScore(userExam.getScore());
            dto.setPassingScore(exam.getPassingScore());
            dto.setFinishedAt(userExam.getRegDate());
            log.info(dto.toString());
            examResultDTOS.add(dto);
        }
        return examResultDTOS;
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

    public void updateUserExamResult(String userToken, Long examId, ExamResultDTO resultDTO) {
        User user = getUserFromToken(userToken);
        log.info(user.toString());
        UserExam userExam = new UserExam(user.getId(), examId, resultDTO.getScore(), resultDTO.getStatus());
        userExamRepo.save(userExam);
    }

    User getUserFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return userRepo.findUserByUsername(username);
    }
}
