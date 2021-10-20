package com.luongtx.oes.service.impl;

import com.luongtx.oes.dto.ExamDTO;
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
import com.luongtx.oes.service.utils.FileUtils;
import com.luongtx.oes.service.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${upload.path}")
    String uploadPath;

    public List<Exam> findAll() {
        return examRepo.findAll();
    }

    public Exam findById(Long id) {
        return examRepo.findById(id).orElse(new Exam());
    }

    @Override
    public ExamDTO findDetailById(Long id) {
        Exam exam = findById(id);
        int numberOfQuestions = findNumberOfQuestions(id);
        exam.setNumberOfQuestions(numberOfQuestions);
        return convertEntityToDTO(exam);
    }

    @Override
    @Transactional
    public ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers) {
        ExamResultDTO resultDTO = new ExamResultDTO();
        Exam exam = examRepo.getById(examId);
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
            Exam exam = examRepo.getById(userExam.getExamId());
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
        return examResultDTOS.subList(0, 5);
    }

    @Override
    public void save(ExamDTO examDTO) {
        Exam exam = convertDTOToEntity(examDTO);
        examRepo.save(exam);
    }

    @Override
    public void save(ExamDTO examDTO, MultipartFile file) {
        Exam exam = convertDTOToEntity(examDTO);
        String imageSource = FileUtils.uploadFile(file, uploadPath);
        if (imageSource != null) {
            exam.setBannerImage(imageSource);
        }
        examRepo.save(exam);
    }

    @Override
    public Long uploadBanner(MultipartFile file, Long examId) {
        Exam exam = new Exam();
        if (examId != null) {
            exam = findById(examId);
        }
        String imageSource = FileUtils.uploadFile(file, uploadPath);
        exam.setBannerImage(imageSource);
        Exam savedExam = examRepo.save(exam);
        return savedExam.getId();
    }

    @Override
    public void delete(Long id) {
        examRepo.deleteById(id);
    }

    @Override
    public void saveQuestions(List<Question> questions, Long examId) {
        Exam exam = examRepo.getById(examId);
        exam.setQuestions(questions);
        examRepo.save(exam);
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

    Exam convertDTOToEntity(ExamDTO dto) {
        Exam exam = new Exam();
        if (dto.getId() != null) {
            exam = examRepo.getById(dto.getId());
        }
        exam.setExamCode(dto.getExamCode());
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setPassingScore(dto.getPassingScore());
        exam.setDuration(dto.getDuration());
//        exam.setQuestions(dto.getQuestions());
        return exam;
    }

    ExamDTO convertEntityToDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setExamCode(exam.getExamCode());
        examDTO.setTitle(exam.getTitle());
        examDTO.setDescription(exam.getDescription());
        examDTO.setPassingScore(exam.getPassingScore());
        examDTO.setDuration(exam.getDuration());
//        examDTO.setQuestions(exam.getQuestions());
        examDTO.setNumberOfQuestions(exam.getNumberOfQuestions());
        String bannerImageSource = ImageUtils.encodeToBased64(exam.getBannerImage());
        examDTO.setBannerImageSource(bannerImageSource);
        return examDTO;
    }

}
